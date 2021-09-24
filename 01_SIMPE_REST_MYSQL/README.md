### mysql rest
This is s simple rest application build using jpa and mysql.

### What are we going to build
We are going to build a simple rest API that will be able to communicate from client to backend using Postman
as our client and spring boot jpa as our backend. Note that we are using Java as our programming language of cause 
because why non!

### Initializing the project
We are going to initialize the project using [start.spring.io](https://start.spring.io/) and select all the 
dependencies that we are going to use in thi project.

If you want exactly the same dependencies as mine you can get them [here](https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.5.5&packaging=jar&jvmVersion=17&groupId=com.mysql&artifactId=mysql&name=mysql&description=using%20mysql%20and%20jpa%20to%20create%20a%20simple%20rest%20api%20in%20java&packageName=com.mysql.mysql&dependencies=web,web,mysql,data-jpa)

Here are the dependencies that I selected for this project:

1. web
2. mysql
3. data-jpa


In our application properties we are going to add the following configurations so that we will be able to connect to
our database eaisly. You can get the properties [here](https://spring.io/guides/gs/accessing-data-mysql/).

```properties
# mysql connection
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/employees
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name =com.mysql.jdbc.Driver
spring.jpa.show-sql: true
```
Next we are going to create a database called `employees` using mysql shell by running the following command.
```roomsql
CREATE DATABASE IF NOT EXISTS employees;
```

Next we are going to create a package called ``employee``. This package will contain all the classes, interfaces related
to employee. So the following will be our basic table

```java
package com.mysql.mysql.employee;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "employees")
public class Employee implements Serializable {
    
    @Id
    @SequenceGenerator(
            name = "employee_sequence",
            sequenceName = "employee_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "employee_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    @Column(nullable = false)
    private String name;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    public Employee(){}
    public Employee(String name, String email){
        this.name = name;
        this.email= email;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
```
It s very simple we have a table Employee and which will be renamed ``employee`` and it have three columns id, email, full_name. We implement constructors, setters and getters. And we also
implement the Employee class to the Serializable interface, and we also have a toString() method.

### Repository
Next we are going to create an Employee repository. We are going to create an Interface Employee 
```java
//employee/EmployeeRepository.java
package com.mysql.mysql.employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}

```
### Service
Next we are going to create a Service. A service helps us to interact with our database.

```java

//employee/EmployeeService.java

package com.mysql.mysql.employee;

import com.mysql.mysql.employee.exceptions.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository repository;
    @Autowired
    public EmployeeService(EmployeeRepository repository){
        this.repository = repository;
    }

    // Adding employees to the database
     public Employee addEmployee(Employee employee){
        return  this.repository.save(employee);
     }
    // Getting all employees
    public List<Employee> getEmployees(){
        return this.repository.findAll();
    }
    // Getting a single employee
    public  Employee getEmployee(Long id){
        return this.repository.findById(id).orElseThrow(
                ()-> new EmployeeNotFoundException("the employee with that id was not found."));
    }
    // Updating an employee
    public Employee updateEmployee(String email, Long id){
        Employee employee = this.repository.findById(id).orElseThrow(()-> new EmployeeNotFoundException("the employee with that id was not found."));
        if(email != null && !email.equals(employee.getEmail())){
            employee.setEmail(email);
        }
        return employee;
    }
    // deleting an employee

    public Boolean deleteEmployee(Long id){
        this.repository.deleteById(id);
        return true;
    }
    // delete all employees
    public Boolean deleteAllEmployees(){
        this.repository.deleteAll();
        return true;
    }

}
```

We are annotating the Employee service with ``@Service`` so that JPA will know that this is a service. Again we can annotate with
`@Component` instead but to be more specific we need `@Service` annotation.


We also used ``@Transactional`` annotation. This annotation is responsible for rollbacks if the SQL query does not go well.
There is a good explanation to this on [stackoverflow](https://stackoverflow.com/questions/54326306/what-is-the-use-of-transactional-with-jpa-and-hibernate/54326467)


We also have custom ``EmployeeNotFoundException`` exception which can be found in exteptions package and it looks as follows:

```java
package com.mysql.mysql.employee.exceptions;

public class EmployeeNotFoundException extends RuntimeException{
    public  EmployeeNotFoundException(String message){
        super(message);
    }
}
```

### Controller
This helps our client and backend to communicate with each other. All the code of the ``EmployeeController`` will be found in
``employee/EmployeeController`` java file and it looks as follows:

```java
//employee/EmployeeController.java
        
package com.mysql.mysql.employee;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/employees")
public class EmployeeController {
    private  final  EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Employee>> getAll(){
        return new ResponseEntity<List<Employee>>(this.service.getEmployees(), HttpStatus.OK);
    }
    @GetMapping(path = "/one/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") Long id){
        return  new ResponseEntity<Employee>(this.service.getEmployee(id), HttpStatus.OK);
    }
    @PostMapping(path = "/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        return  new ResponseEntity<Employee>(
                this.service.addEmployee(employee), HttpStatus.CREATED
        );
    }
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee,
                                                   @PathVariable("id") Long id){
        return  new ResponseEntity<Employee>(
                this.service.updateEmployee(employee.getEmail(), id), HttpStatus.OK
        );
    }
    @DeleteMapping(path = "/delete-one/{id}")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable("id") Long id){
        return  new ResponseEntity<Boolean>(this.service.deleteEmployee(id), HttpStatus.OK);
    }
    @DeleteMapping(path = "/delete-all")
    public ResponseEntity<Boolean> deleteAll(){
        return  new ResponseEntity<Boolean>(this.service.deleteAllEmployees(), HttpStatus.OK);
    }
}
```

### Testing the API using Postman

We are going to use Postman to test our API endpoints. I will explain every piece of code that i wrote in the ``employee/EmployeeController.java`` file

### Getting all the employees

The request method should be ``GET``
```java
@GetMapping(path = "/all")
    public ResponseEntity<List<Employee>> getAll(){
        return new ResponseEntity<List<Employee>>(this.service.getEmployees(), HttpStatus.OK);
}
```
This route will get all the employees when we visit the http://127.0.0.1:8080/api/v1/employees/all and in my case i will get 
the following json response

```json
[
    {
        "id": 53,
        "name": "username3 surname2",
        "email": "hello@gmail.com"
    }
]
```

### Getting a single employee

The method should be a ``GET`` method with id as a path variable that already exist in the database otherwise the error will be thrown.

```java
 @GetMapping(path = "/one/{id}")
public ResponseEntity<Employee> getEmployee(@PathVariable("id") Long id){
        return  new ResponseEntity<Employee>(this.service.getEmployee(id), HttpStatus.OK);
}
```
This route will get an employee of id 53 when we visit the http://127.0.0.1:8080/api/v1/employees/53 and in my case i will get
the following json response

```json
 {
  "id": 53,
  "name": "username3 surname2",
  "email": "hello@gmail.com"
}
```


### Adding an employee to the database

The request method should be ``POST`` with username and email required and the email should be unique.

```java
@PostMapping(path = "/add")
public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        return  new ResponseEntity<Employee>(
        this.service.addEmployee(employee), HttpStatus.CREATED
        );
}
```
This route will add an employee to the database when we visit the http://127.0.0.1:8080/api/v1/employees/add and supply the following 
json body 
```json
{
    "name": "username4 surname2",
    "email": "username4@gmail.com"
}
```
We will get the following 
```json
{
  "id": 102,
  "name": "username4 surname2",
  "email": "username4@gmail.com"
}
```

### Updating a user

We need to supply the employee id as a path variable and email of the employee in the request body as a `PUT` request. Note that the email that
will be updated must be unique from the ones that already exists in teh database, otherwise an error will be thrown.


```java
@PutMapping(path = "/update/{id}")
public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee,
@PathVariable("id") Long id){
        return  new ResponseEntity<Employee>(
        this.service.updateEmployee(employee.getEmail(), id), HttpStatus.OK
        );
}
```
This route will update an employee of id 102 when we visit the http://127.0.0.1:8080/api/v1/employees/upadte/102 and supply the following request body

**Note:** ``102`` is the id that exists in the database. If it does not exist it will throw an error 
```json
{
    "email": "hell4@gmail.com"
}
```
We will get
```json
{
  "id": 102,
  "name": "username4 surname2",
  "email": "hell4@gmail.com"
}
```

### Deleting an employee
To delete an employee we supply an id to teh path variable and send a ``DELETE``requests with the employee id that already in the database
otherwise we will get an error.

```java
@DeleteMapping(path = "/delete-one/{id}")
public ResponseEntity<Boolean> deleteEmployee(@PathVariable("id") Long id){
        return  new ResponseEntity<Boolean>(this.service.deleteEmployee(id), HttpStatus.OK);
}
```
This route will delete an employee of id 53 when we visit the http://127.0.0.1:8080/api/v1/employees/delete-one/53 and in my case i will get
the following json response

```json
true
```
### Deleting all employees
When a `DELETE` request is sent to the server at http://127.0.0.1:8080/api/v1/employees/delete-all then all the employees will be deleted in the database
and we will get the following response back.
```json
true
```

That's all I wanted to go through in this one.