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
many things but relations and how they work in data-jpa. We are going to have the following tables in our database
`department`.

1. Lecturer
2. Course
3. Students

* Lecturer will have many Students and a single Course
* Course will have one Lecture and many Students
* Students will have many Lecturers and many courses.

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

### Creating Entities

We are going to have 3 packages:
1. student
* contains all things related to the student
2. course
* contains all things related to the course
3. lecturer
* contains all things related to the lecturer

As i said the purpose of this is to show how relations works in spring data jpa. We are going to have the following
entities to begin with:

1. Student
```java
package com.relations.relations.student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {
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
    private String name;
}

```
2. Lecturer
```java
package com.relations.relations.lecturer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="lecturers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lecturer implements Serializable {
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
    private String name;
}

```
3. Course
```java
package com.relations.relations.course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course implements Serializable {
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
    @Column(nullable = false, columnDefinition = "VARCHAR(7) NOT NULL")
    private String code;
}
```

### One to One relation
If you remember we said a single lecturer will have a single course and a single course
belongs to a single lecturer. So we want to create a `1-1` bidirectional relation between these two entities.


https://github.com/kriscfoster/Spring-Data-JPA-Relationships/blob/master/src/main/java/com/kriscfoster/school/subject/Subject.java





















