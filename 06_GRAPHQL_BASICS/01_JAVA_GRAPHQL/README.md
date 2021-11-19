### Graph QL in Java

In this Readme we are going to create a simple graphql api by following [this blog post](https://developer.okta.com/blog/2020/01/31/java-graphql)

First we are going to go to the [https://start.spring.io/](https://start.spring.io/) and add somed dependencies to it. We are going to add the following dependencies:

1. Spring web
2. Spring Data JPA
3. Lombok
4. MySQL Driver


If you want the exact packages that i used you can get them [here](https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.5.7&packaging=jar&jvmVersion=17&groupId=com.example&artifactId=graphql&name=graphql&description=A%20simple%20graphql%20api%20in%20java.&packageName=com.example.graphql&dependencies=web,lombok,mysql,data-jpa)

We are then going to go to the `pom.xml` and add the [GraphQL SPQR](https://github.com/leangen/graphql-spqr) as a dependence to the `pom.xml`.

```xml
<dependency>
    <groupId>io.leangen.graphql</groupId>
    <artifactId>spqr</artifactId>
    <version>0.11.2</version>
</dependency>
```

### Spring Boot Dev Tools
We want our application to automatically restart on code changes, so we are going to add Spring Boot Dev Tools, so in the `pom.xml` we are also going do add
the following dependecy. You can find the explanation on how to configure that [here](https://github.com/CrispenGari/java-backend/tree/main/05_SPRING_AND_THYMELEAF/00_thymeleaf)

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

### Application properties

We are then going to configure the `application.yml` file to look as follows for the database connection,
server configuration and graphql spr config

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
    url: jdbc:mysql://localhost:3306/graphql
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver

graphql:
  spqr:
    gui:
      enabled=true:
server:
  port: 3001

```

### What are we going to build?

We are going to build an api that will be able to perform some CRUD operations on the `Food` entity.


### Food entity

First let's create a food package and add the `Food` entity in the `Food` class as follows:

```java
package com.example.graphql.food;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="foods")
@NoArgsConstructor
public class Food implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GraphQLQuery(name = "id", description = "A food's id")
    private Long id;

    @GraphQLQuery(name = "name", description = "A food's name")
    private String name;
}
```

Next we are going to create a `Food` repository which will look as follows

```java
package com.example.graphql.food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
}
```

Next we are going to create a Service Interface which will be implemented with the FoodService as follows:

```java
package com.example.graphql.food;

import java.util.Collection;

public interface FoodServiceInterface {
    Collection<Food> getFoods();
    Food addFood(Food food);
    Food getFoodById(Long id);
    Food updateFood(Food food);
    Boolean deleteFood(Long id);
}


```
The `FoodService` will look as follows:

```java
```



### Ref
* [developer.okta.com]((https://developer.okta.com/blog/2020/01/31/java-graphql)
