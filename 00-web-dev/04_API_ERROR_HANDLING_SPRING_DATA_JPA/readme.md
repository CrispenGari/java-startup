### Handling API errors Spring Data JPA

In this one we are going to learn how to handle exceptions for API's in spring data jpa. We are going to have a
good and clear explanation and create a simple REST api that will do CRUD operations.


We are going to create an Animal database. First we are going to open the `application.yml` file and populate it with the following properties

```yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/animals
    username: postgres
    password: root
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true

# Port
server:
  port: 3001
```

We are using Postgres as our database driver. If you want to get the same boiler plate that
 i created on [start.spring.io](https://start.spring.io/)
 click [this link](https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.5.6&packaging=jar&jvmVersion=17&groupId=com.errors&artifactId=errors&name=errors&description=Demo%20project%20for%20Spring%20Boot&packageName=com.errors.errors&dependencies=lombok,web,data-jpa,postgresql).
 Note that I'm on java 17.

### Animal entity

We are then going to create an animal package. This package will contain all the code related to the animal
including controllers, repositories, service and entity.

### Animal entity
The animal entity will look as follows:

```java
// Animal.java
package com.errors.errors.animal;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="animals")
@Data
@NoArgsConstructor
public class Animal implements Serializable {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false, unique = false, length = 255)
    private  String name;
}
```

### Animal repository
The animal repository will look as follows:

```java
// AnimalRepository.java

package com.errors.errors.animal;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AnimalRepository extends JpaRepository<Animal, Long> {
}

```
### Animal Service Interface
The animal service interface will look as follows:

```java
// AnimalServiceInterface.java

package com.errors.errors.animal;
import java.util.Collection;

public interface AnimalServiceInterface {
    Animal addAnimal(Animal animal);
    Animal getAnimal(Long id);
    Boolean deleteAnimal(Long id);
    Animal updateAnimal(Animal animal);
    Collection<Animal> getAnimals();
}

```

### Animal Service Implementation
The animal service Implementation will look as follows

```java
// AnimalService.java

package com.errors.errors.animal;

import com.errors.errors.exceptions.AnimalNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
@RequiredArgsConstructor
public class AnimalService implements AnimalServiceInterface{

    private final AnimalRepository repository;
    @Override
    public Animal addAnimal(Animal animal) {
        return this.repository.save(animal);
    }

    @Override
    public Animal getAnimal(Long id) {
        return this.repository.findById(id).orElseThrow(()-> new AnimalNotFoundException("the animal with id: " + id + "was not found"));
    }

    @Override
    public Boolean deleteAnimal(Long id) {
        this.repository.deleteById(id);
        return true;
    }

    @Override
    public Animal updateAnimal(Animal animal) {
        return this.repository.save(animal);
    }

    @Override
    public Collection<Animal> getAnimals() {
        return this.repository.findAll();
    }
}

```

### AnimalNotFoundException

This is a custom exception which is found in the `exception` package and it looks as follows:

```java
package com.errors.errors.exceptions;
public class AnimalNotFoundException extends RuntimeException{
    public AnimalNotFoundException(String message){
        super(message);
    }
}
```


### Animal Controller
The animal controller will look as follows:

```java
package com.errors.errors.animal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "api/v1/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService service;

    @PostMapping(path = "/create")
    public ResponseEntity<Animal> createAnimal(@RequestBody Animal animal){
        return ResponseEntity.status(201).body(this.service.addAnimal(animal));
    }


    @PutMapping("/update/{animalId}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable("animalId") Long animalId, @RequestBody Animal animal){
        Animal animal1 = this.service.getAnimal(animalId);
        animal1.setName(animal.getName());
        return ResponseEntity.status(204).body(this.service.updateAnimal(animal1));
    }

    @DeleteMapping("/delete/{animalId}")
    public ResponseEntity<Boolean> deleteAnimal(@PathVariable("animalId") Long animalId){
        return ResponseEntity.status(204).body(this.service.deleteAnimal(animalId));
    }

    @GetMapping("/animal/{animalId}")
    public ResponseEntity<Animal> getAnimal(@PathVariable("animalId") Long animalId){
        return ResponseEntity.status(200).body(this.service.getAnimal(animalId));
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Animal>> getAnimals(){
        return ResponseEntity.status(200).body(this.service.getAnimals());
    }
}
```

Now we can be able to make api requests to the server. But when we hit the api server at http://localhost:3001/api/v1/animals/animal/1 considering that the animal
with id `1` does not exists we will get the following error response:

```json
{
    "timestamp": "2021-11-04T08:24:53.015+00:00",
    "status": 500,
    "error": "Internal Server Error",
    "path": "/api/v1/animals/animal/1"
}
```
We want to customize this error so that we will get a meaningful error like `"animal with id 1, not found"`.


### Handling errors

There are  two ways of handling exception which are:

1. Global exception handling
2. Individual exception handling

And we will show all this in example.
First we need to go to the `exception` package and create the ErrorType which will look as follows:

```java
import lombok.*;
import java.util.Date;
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ErrorType {
    private Date timestamp;
    private String message;
    private String details;
}
```

Next we will then create a `GlobalExceptionHandler` which will have the following code in it.

```java
// GlobalExceptionHandler.java

package com.errors.errors.exceptions;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

//    Handling specific exception

    @ExceptionHandler(AnimalNotFoundException.class)
    public ResponseEntity<?> animalNotFound(AnimalNotFoundException exception, WebRequest request){
        ErrorType errorType = new ErrorType(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorType, HttpStatus.NOT_FOUND);
    }

//    Global exception handling
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request){
        return new ResponseEntity<>(
                new ErrorType(new Date(), exception.getMessage(), request.getDescription(false))
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

Not that we must annotate our  `GlobalExceptionHandler` with `@ControllerAdvice` and for each exception both global and individual
exception we have to annotate them with `@ExceptionHandler()`.

Now if we go to `http://localhost:3001/api/v1/animals/animal/7`  in the event that the animal of id `7` does note exists in the database
we will get the following response:

```json
{
    "timestamp": "2021-11-04T08:46:19.752+00:00",
    "message": "the animal with id: 7was not found",
    "details": "uri=/api/v1/animals/animal/7"
}
```

> One thing to take note of is that we can have multiple exception and handle them 1 by one and exception handling
is a good way to go and helps us to get meaningful error messages in an api from the frontend.