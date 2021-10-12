### Spring Data JPA relations

In this one we are going to create a simple rest api that will map the following relations using spring data jpa and mysql driver.

1. One-One relationship
2. Many-One/One-Many relations
3. Many to Many relations

### Getting started
I went to [start.spring.io](https://start.spring.io/) and initialize my project, you can get all the copy of the bootstraped
code version of my project [](https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.5.5&packaging=jar&jvmVersion=17&groupId=com.relations&artifactId=relations&name=relations&description=basic%20relations%20in%20spring%20data%20jpa&packageName=com.relations.relations&dependencies=web,web,lombok,data-jpa,data-jpa,mysql)
I am using the following dependencies

1. web
2. lombok
3. data-jpa
4. mysql

### What are we going to be creating?
We will be creating a simple rest api as I said to map all the mentioned relations. So we are not mainly focused on
many things but relations and how they work in data-jpa.

### Database
As i said i will be using mysql, feel free to use whatever database you want. In my case i will open the mysql command
prompt and create a new database called `department` by running the following command.

```sql
CREATE DATABASE IF NOT EXISTS department;
```

#### Creating a connection
We are now ready to create a connection, so we are going to rename `application.properties` to `application.yml` and add
the following configurations to it:

```yml
# Application properties
spring:
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
      properties:
        hibernate:
          dialect: org.hibernate.dialect.MySQL8Dialect
          format_sql: true
  datasource:
    url: jdbc:mysql://localhost:3306/department
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver

server:
  port: 3001
```

### 1. One-to-One `uni-directional` Relationship

For this example we are going to create a new package called `one2one`. This package will have two packages in it
1. `book` package
This package contains all the code that is related to the `book` entity/table

2. `picture` package

This package will contain all the code tha is related to the `picture` entity/table

> The book and the picture has a one to one relationship that we are going to define in the `book/Book.java` class which looks as follows:


```
[Book] --------------->[Picture]
      1               1
```
```java
package com.relations.relations.one2one.book;

import com.relations.relations.one2one.picture.Picture;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="books")
@NoArgsConstructor
public class Book implements Serializable {
    @SequenceGenerator(
            name="post_sequence",
            allocationSize = 1,
            sequenceName = "post_sequence"
    )
    @GeneratedValue(
            generator = "post_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @Id
    private Long id;
    @Column(nullable = false)
    private  String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="picture_id", nullable = false)
    private Picture picture;
}
```
We are creating a `1-to-1` relationship with a `picture` and we are using the `@JoinColumn` and pass the the `picture_id`
as the foreign key in this table.

Then moving to the `picture/Picture` entity we defined it as follows:

```java
package com.relations.relations.one2one.picture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="pictures")
@NoArgsConstructor
@AllArgsConstructor
public class Picture implements Serializable {
    @SequenceGenerator(
            name="post_sequence",
            allocationSize = 1,
            sequenceName = "post_sequence"
    )
    @GeneratedValue(
            generator = "post_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @Id
    private Long id;
    @Column(nullable = false)
    private String url;
}
```

We are not using the `@OneToOne` annotation here since this relationship is going to be unidirectional. This is how we
can create a one to one relationship between the book and the picture.

> **All the controllers code and services code will be found in the respective packages. For example the controllers
of the picture will be found in the picture package in the file named `PictureController.java`.**


### 2. One-to-One `bi-directional` Relationship

To make the above relationship bidirectional, we need to do few things, first we need to to edit our `Picture` entity
to look as follows:

```
[Book]<--------------->[Picture]
      1               1
```


```java
package com.relations.relations.one2one.picture;
import com.relations.relations.one2one.book.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="pictures")
@NoArgsConstructor
@AllArgsConstructor
public class Picture implements Serializable {
    @SequenceGenerator(
            name="post_sequence",
            allocationSize = 1,
            sequenceName = "post_sequence"
    )
    @GeneratedValue(
            generator = "post_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @Id
    private Long id;
    @Column(nullable = false)
    private String url;

    @OneToOne(mappedBy = "picture")
    private Book book;

    public Picture(String url){
        this.url = url;
    }
}
```

We created another constructor with `url` using the java's idea of constructor overloading. This will allow us to
only pass the `url` to the Picture class as a string, so that we will be able to create a new picture as follows;

```java
Picture picture = new Picture(url)
```

We are going to leave the Book entity as it is and we will go to the `BooksController` and add the following class in it:

```java
@Data
class BookPictureInput{
    private Book book;
    private Picture picture;
}
```

This class allows us to take the input as a json in the following format:

```json
{
    "book": {
        "title": "cats"
    },
    "picture": {
        "url": "http://fake.pictures.com/cats/4567.png"
    }
}
```
Next we are then going to create a `PostMapping` endpoint that serve at `/create/picture` this allows us to create a
book and a picture at the same time and it looks as follows:

```java
  @PostMapping(path = "/create/picture")
    public  ResponseEntity<Book> createBookWithAPicture(@RequestBody BookPictureInput input){
        Picture picture = new Picture(input.getPicture().getUrl());
        Book book = input.getBook();
        book.setPicture(picture);
        return ResponseEntity.status(201).body(this.bookService.createBook(book));
    }
```

Now if we go to postman we can now be able to send the POST request at http://localhost:3002/api/v1/books/create/picture together with the following
json body:

```json
{
    "book": {
        "title": "cats"
    },
    "picture": {
        "url": "http://fake.pictures.com/cats/4567.png"
    }
}
```

To get the following json response:

```json
{
    "id": 1,
    "title": "cats",
    "picture": {
        "id": 2,
        "url": "http://fake.pictures.com/cats/4567.png",
        "book": null
    }
}
```

Now we have implemented a bidirectional 1 to 1 relationship, next we are going to implement the one-many relationship.


### 3. one-to-many/many-to-one relationship

For this relationship we are going to create two packages the `lecturer` and the `student` package. Basically the lecturer
will have a 1 to many relationship with his students.

```
[Lecturer]<--------------->[Students]
          1               N
```

### Defining the Domain Models

Before moving fast and learn, we want to learn something which is sharing field between two entities. Let's consider
our example of a lecturer and students. We can create database columns such as `createdAt` and `updatedAt` for both
the student and lecturer entity. But to obey the `dry` "do not repeat yourself" we are going to abstract those two
common fields in their own class, then we will extends them to both the lecture and student entities. So we are going to create
another package called `common` where we will put all the code that will be shared between lecturer and student. In
our case we are going to share the absracted class called `AuditModel` which will contain the `createdAt` and `updatedAt`
columns:


```java
package com.relations.relations.one2many.common;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditModel {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at", nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_at", nullable = false, updatable = false)
    @LastModifiedDate
    private Date updatedAt;
}
```

We’ll also use [Spring Boot’s JPA Auditing](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.auditing) feature
to automatically populate the `createdAt` and `updatedAt` fields while persisting the entities.

In the above class, we’re using Spring Boot’s `AuditingEntityListener` to automatically populate the createdAt and updatedAt fields.


> Inoder for this to work we need to go to the main class and in our case `RelationshipApplication.java` and anotate the application
with `@EnableJpaAuditing` as follows:

```java
package com.relations.relations;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@SpringBootApplication
@EnableJpaAuditing
public class RelationsApplication {
	public static void main(String[] args) {
		SpringApplication.run(RelationsApplication.class, args);
	}
}
```

### Lecturer Entity

The lecturer entity will look as follows:

```java
package com.relations.relations.one2many.lecturer;
import com.relations.relations.one2many.common.AuditModel;
import com.relations.relations.one2many.student.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name="lectures")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Lecturer extends AuditModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "lecturer")
    @Fetch(value= FetchMode.SELECT)
    private Collection<Student> students;

}
```

### Student Entity
The student entity will look as follows:

```java

package com.relations.relations.one2many.student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.relations.relations.one2many.common.AuditModel;
import com.relations.relations.one2many.lecturer.Lecturer;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="student")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Student extends AuditModel  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lecturer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Lecturer lecturer;
}
```

We are adding a `@JsonIgnore` since we don't care much about the `lecturer` of the student in the student's service. This will
also remove the `recursive` behaviour of one to many relationship between these tow entities.

> Note that all the `services` and `controllers` of each entity is on their packages. Student code related will be
on the `student` package and `lecturer` will be on the lecturer package.

Now we are able to create a lecturer.

Hitting the following url http://localhost:3002/api/v1/lecturers/create with the following `json` body:
```json
{
   "name": "lecturer3"
}
```

We will get the following response

```json
{
    "createdAt": "2021-10-12T15:39:28.067+00:00",
    "updatedAt": "2021-10-12T15:39:28.067+00:00",
    "id": 3,
    "name": "lecturer3",
    "students": null
}
```

Now that we have a lecturer, we can now add students to the lecturer by hitting the following url http://localhost:3002/api/v1/students/create/3 with
the following json body:

> Note that we have a `pathVariable` 3 which is the id of the lecturer that we are adding students to.

```json
{
   "name": "student4"
}
```

Add as many student as you want.

Now to get the lecturer and his/her student we hit the following url with a GET http://localhost:3002/api/v1/lecturers/one/3
 request where `3` is the id of the `lecturer`. We will get the following response if everything went well

 ```json
{
    "createdAt": "2021-10-12T15:39:28.067+00:00",
    "updatedAt": "2021-10-12T15:39:28.067+00:00",
    "id": 3,
    "name": "lecturer3",
    "students": [
        {
            "createdAt": "2021-10-12T15:39:38.455+00:00",
            "updatedAt": "2021-10-12T15:39:38.455+00:00",
            "id": 3,
            "name": "student3"
        },
        {
            "createdAt": "2021-10-12T15:39:43.052+00:00",
            "updatedAt": "2021-10-12T15:39:43.052+00:00",
            "id": 4,
            "name": "student1"
        },
        {
            "createdAt": "2021-10-12T15:39:47.314+00:00",
            "updatedAt": "2021-10-12T15:39:47.314+00:00",
            "id": 5,
            "name": "student4"
        }
    ]
}
```

> One to ManyToOne relationship is the reverse of what we did (many 2 one);


### Limitations of bidirectional Relations

1. A bidirectional mapping tightly couples the many-side of the relationship to the one-side.
2. In our example, If you load comments via the post entity, you won’t be able to limit the number of comments loaded. That essentially means that you won’t be able to paginate.
3. If you load comments via the post entity, you won’t be able to sort them based on different properties. You can define a default sorting order using @OrderColumn annotation but that will have performance implications.
4. You’ll find yourself banging your head around something called a LazyInitializationException.


### When to use bidirectional relation?

* A bidirectional one-to-many mapping might be a good idea if the number of child entities is limited.


### 3. Many-2-Many relations

Let's consider a situation where we have a `Question` and for each `Question` we have `Categories`. For these `Categories`
they belong to more than one Question. That's a many to many relation. We are going to create a simple many to many relation
between a Question and a Category. As usual we are going to create  packages for each entity.


We are going to have categories for these question:

1. `SPORT`
2. `BUSINESS`
2. `EDUCATION`
2. `HUMAN`
So we are going to create an `Enum` in the common package and it's code will look as follows

```java
package com.relations.relations.many2many.common;
public enum QuestionCategory {
    SPORT("SPORT"),
    BUSINESS("BUSINESS"),
    EDUCATION("EDUCATION"),
    HUMAN("HUMAN");
    private final String category;
    QuestionCategory(String category){
        this.category =category;
    }
    public String getStatus() {
        return this.category;
    }
}
```

### Categories Entity

The categories entity will be looking as follows:

```java
package com.relations.relations.many2many.category;
import com.relations.relations.many2many.common.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="categories")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Category extends AuditModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String category;

}
```
I'm nog going to use the `@ManyToMany` annotation on the the the categories Entity since it ia a unidirectional many2many relation
between the `Category` and `Question`

### Questions Entity
The question entity will be looking as follows:

```java
package com.relations.relations.many2many.question;
import com.relations.relations.many2many.category.Category;
import com.relations.relations.many2many.common.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
@Entity
@Table(name="questions")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Question extends AuditModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String title;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "questions_categories",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Collection<Category> categories;
}
```

To make the above Relationship bidirectional all we need to do is go to the `Category` entity and add the following
to it:

```java
....
public class Category extends AuditModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String category;


    @ManyToMany(mappedBy = "categories")
    private Collection<Question> books;

}
...
```

Now since it is a `unidirectional` relationship we have to add categories first and then when creating a question
we then going to pass the `categoryIds` together with the question as a RequestBody of the type QuestionInput which looks
as follows:

```java
@Data
class QuestionInput{
    private Question question;
    private Collection<Long> categoryIds;
}
```

So now we will be able to create a `Question` with it's categories at http://localhost:3002/api/v1/questions/add with the following json body


```json
{
    "question": {
        "title": "what is your name?"
    },
    "categoryIds": [
        1,
        2,
        3
    ]
}
```

We will get the following json response

```json
{
    "createdAt": "2021-10-12T17:39:44.601+00:00",
    "updatedAt": "2021-10-12T17:39:44.601+00:00",
    "id": 1,
    "title": "what is your name?",
    "categories": [
        {
            "createdAt": "2021-10-12T17:38:36.000+00:00",
            "updatedAt": "2021-10-12T17:38:36.000+00:00",
            "id": 1,
            "category": "HUMAN"
        },
        {
            "createdAt": "2021-10-12T17:38:43.428+00:00",
            "updatedAt": "2021-10-12T17:38:43.428+00:00",
            "id": 2,
            "category": "BUSINESS"
        },
        {
            "createdAt": "2021-10-12T17:38:56.011+00:00",
            "updatedAt": "2021-10-12T17:38:56.011+00:00",
            "id": 3,
            "category": "EDUCATION"
        }
    ]
}
```

This is handled internaly in teh `QuestionController` which have the following code in it:


```java
package com.relations.relations.many2many.question;

import com.relations.relations.many2many.category.Category;
import com.relations.relations.many2many.category.CategoryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
@RestController
@RequestMapping(path = "/api/v1/questions")
@RequiredArgsConstructor
public class QuestionController {
    private  final CategoryService categoryService;
    private  final QuestionService questionService;
    @PostMapping(path="/add")
    public ResponseEntity<Question> createCategory(@RequestBody QuestionInput input){
        Collection<Category>  categories = this.categoryService.getAllCategoriesByIds(input.getCategoryIds());
        input.getQuestion().setCategories(categories);
        return ResponseEntity.status(201).body(this.questionService.createQuestion(input.getQuestion()));
    }

    @GetMapping(path="/all")
    public ResponseEntity<Collection<Question>> getCategories(){
        return ResponseEntity.status(200).body(this.questionService.getQuestions());
    }

    @GetMapping(path="/one/{questionId}")
    public ResponseEntity<Question> getCategory(@PathVariable("questionId") Long questionId){
        return ResponseEntity.status(200).body(this.questionService.getQuestion(questionId));
    }
}
```

> All the remaining code will be in the files.
### Refs

1. [huawei-developers](https://medium.com/huawei-developers/database-relationships-in-spring-data-jpa-8d7181f50f60)
2. [callicoder](https://www.callicoder.com/hibernate-spring-boot-jpa-one-to-many-mapping-example/)






















