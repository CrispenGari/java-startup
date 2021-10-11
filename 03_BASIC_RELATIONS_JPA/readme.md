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


### 3. one-to-many/many-to-one unidirectional relationship


### Refs

1. [huawei-developers](https://medium.com/huawei-developers/database-relationships-in-spring-data-jpa-8d7181f50f60)
2. [callicoder](https://www.callicoder.com/hibernate-spring-boot-jpa-one-to-many-mapping-example/)






















