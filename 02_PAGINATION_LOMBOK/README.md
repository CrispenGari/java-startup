### Pagination Spring boot and Postgres

In this one we are going to create a simple pagination API and show how to configure ``CORS`` for our application. We are going to
use the following dependencies:

1. web
2. postgresql
3. data-jpa
4. validation
5. lombok

### Getting started 
Go to the [start.spring.io](https://start.spring.io) and initialize the project and add the above dependencies, or you can 
[click here](https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.5.5&packaging=jar&jvmVersion=17&groupId=com.pagination&artifactId=pagination&name=pagination&description=Demo%20project%20for%20Spring%20Boot&packageName=com.pagination.pagination&dependencies=web,postgresql,postgresql,data-jpa,validation,validation,lombok)
to download this current starter.

### What are we going to build?
We are going to create a simple RestAPI with pagination. We will create a simple Post and create pagination based on this
entity. We are going to make use of the Lombok dependence so that it will initialize getters, setters for us in our Post
entity/table. We will see this as we go. First lets get started.

We will rename our ``application.properties`` to `application.yml` so that we will use nice `yml` synthax that comes 
with it to define our application properties as follows:

```yaml

# postgres sql connection
spring:
  datasource:
    password: root
    url: jdbc:postgresql://localhost:5432/posts
    username: postgres
  jpa:
    hibernate:
      ddl-auto: create #create
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
    show-sql: true
# Configuring the port for our server
server:
  port: 3001
```

### Creating a post database
To create a posts database we are going to run the following command in `psql` shell.

```roomsql
CREATE DATABASE posts;
```
Next we are going to create a package called ``posts`` this package will contain all the files related to the `post`.

### Creating a Post Entity
We are going to create our `Post` entity in `posts/Post.java` class.

```java
// posts/Post.java

package com.pagination.pagination.posts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table(name="posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post implements Serializable {
    @Id
    @SequenceGenerator(
            name="post_sequence",
            allocationSize = 1,
            sequenceName = "post_sequence"
    )
    @GeneratedValue(
            generator = "post_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    @Column(name = "caption", nullable = false)
    private String caption;
    private Status status;
    @Column(unique = true)
    @NotEmpty(message = "the email address is required")
    private String email;
}
```
We are using some validation and some ``Lombok`` annotations here let me go through them very quick.

1. ``@Data`` - is responsible for our getters and setters.
2. ``@NoArgsConstructor`` - this will generate constructor with no argument.
3. ``@AllArgsConstructor`` - this will generate constructor for the decorated class with args.

We have created a status Enum type in the `/post/Status.java` file as follows:

```java
package com.pagination.pagination.posts;
public enum Status {
    LIKED("LIKED"),
    UNLIKED("UNLIKED");
    private final String status;
    Status(String status){
        this.status =status;
    }
    public String getStatus() {
        return this.status;
    }
}
```
This is Enumeration type is for our status.

### Creating the Post Repository

Next we are going to create an Interface PostRepository that will extend JpaRepository a follows

````java
// PostRepository.java
package com.pagination.pagination.posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
````

### Response Class
Next we are going to create a response Class. This class is generic, it is the response that will be sent to the
client.

```java
// Response.java
package com.pagination.pagination.posts;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    protected LocalDateTime timeStamp;
    protected int statusCode;
    protected HttpStatus status;
    protected String reason;
    protected int limit;
    protected int page;
    protected Map<?, ?> posts;
}
```
This is the response that we will send to the user. Again we are decorating with @Data from Lombok so tha setters
and getters will be created for us.

We are also using new annotations which are:

1. `@JsonInclude` - specify wether to return null fields or not to the response
2. ``[@SuperBuilder](https://projectlombok.org/features/experimental/SuperBuilder)`` The @SuperBuilder annotation produces complex builder APIs for your classes.

### Creating a Service

Next we are going to create our PostService. But before that we want to create a PostServiceInterface which defines
what we want to will implement in our PostService class.

```java
// PostServiceInterface.java
package com.pagination.pagination.posts;

import java.util.Collection;

public interface PostServiceInterface {
    Post createPost(Post post);
    Post react(Long id);
    Collection<Post> getPosts(int page, int limit);
    Post updatePost(Post post);
    Boolean deletePost(Long id);
    Post getPost(Long id);
}
```

Next we will implement the PostService in the PostService class

```java
// PostService.java
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService implements PostServiceInterface{

    private final PostRepository repository;
    @Override
    public Post createPost(Post post) {
        post.setStatus(Status.UNLIKED);
        return this.repository.save(post);
    }

    @Override
    public Post react(Long id) {
        Post post = this.repository.findById(id).orElseThrow(()-> new CustomException("could not find the post of that id"));
        post.setStatus(post.getStatus() == Status.LIKED ? Status.UNLIKED: Status.LIKED);
        return post;
    }

    @Override
    public Collection<Post> getPosts(int page, int limit) {
        return this.repository.findAll(PageRequest.of(page, limit)).toList();
    }

    @Override
    public Post updatePost(Post post) {
        post.setStatus(Status.LIKED);
        return this.repository.save(post);
    }

    @Override
    public Boolean deletePost(Long id) {
       this.repository.deleteById(id);
       return true;
    }

    @Override
    public Post getPost(Long id) {
        return this.repository.findById(id).orElseThrow(()->new CustomException("the post with that id was found."));
    }
}
```

Once our services are next to us we need to make our API communicate with  the client side, therefore we need to create Controllers

### Post Controller

The post controller will look as follows
````java
// PostController.java

package com.pagination.pagination.posts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    public final PostService service;

    @GetMapping(path = "/all")
    public ResponseEntity<Response> getPosts(
            @RequestParam(name = "page", required = true) int page,
            @RequestParam(name = "limit", required = true) int limit
    ) {
     
        Collection<Post> posts = service.getPosts(page, limit);
        return ResponseEntity.ok(
                Response.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .limit(limit)
                        .page(page)
                        .posts(Map.of("posts", posts))
                        .error(null)
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Response> createPost(@RequestBody Post post){
      
        return ResponseEntity.ok(
                Response.builder()
                        .posts(Map.of("posts", this.service.createPost(post)))
                        .error(null)
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Response> deletePost(@PathVariable("id") Long id) {
        boolean postFound = true;
        ResponseError error = new ResponseError("the post of that id does not exists", "id");
        try {
            this.service.getPost(id);
        }catch (CustomException e){
            postFound = false;
        }
        return ResponseEntity.ok(
                Response.builder()
                        .posts(Map.of("posts", this.service.deletePost(id)))
                        .error(postFound? null : error)
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<Response> updatePost(@PathVariable("id") Long id, @RequestBody Post post) {
        boolean postFound = true;
        ResponseError error = new ResponseError("the post of that id does not exists", "id");
        try {
            this.service.getPost(id);
        }catch (CustomException e){
            postFound = false;
        }
        return ResponseEntity.ok(
                Response.builder()
                        .posts(Map.of("posts", this.service.updatePost(post)))
                        .error(postFound? null : error)
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PutMapping(path = "/react/{id}")
    public ResponseEntity<Response> reactToPost(@PathVariable("id") Long id) {
        boolean postFound = true;
        ResponseError error = new ResponseError("the post of that id does not exists", "id");
        try {
            this.service.getPost(id);
        }catch (CustomException e){
            postFound = false;
        }
        return ResponseEntity.ok(
                Response.builder()
                        .posts(Map.of("posts", this.service.react(id)))
                        .error(postFound? null : error)
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }
}

````
Now with this code we will be able to make API request to this server. We are going to test different API endpoints
using Postman. 

### Getting all posts with pagination
Inorder to get all the post with pagination we are going to pass the page and limit as params to the /all route. for example
in my case if i send a GET request to http://localhost:3001/api/v1/posts/all?page=0&limit=10 i will get the following json
response

```json

{
    "timeStamp": "2021-09-25T13:15:34.5994327",
    "statusCode": 200,
    "status": "OK",
    "limit": 10,
    "page": 0,
    "posts": {
        "posts": [
            {
                "id": 2,
                "caption": "hello2 world",
                "status": "UNLIKED",
                "email": "hell2@gmail.com"
            },
            {
                "id": 3,
                "caption": "hello3 world",
                "status": "UNLIKED",
                "email": "hell3@gmail.com"
            },
            {
                "id": 4,
                "caption": "hello3 world",
                "status": "UNLIKED",
                "email": "hell4@gmail.com"
            },
            {
                "id": 5,
                "caption": "hello3 world",
                "status": "UNLIKED",
                "email": "hell24@gmail.com"
            },
            {
                "id": 7,
                "caption": "hello3 world",
                "status": "UNLIKED",
                "email": "hell244@gmail.com"
            },
            {
                "id": 8,
                "caption": "hello3 world",
                "status": "UNLIKED",
                "email": "hell24444@gmail.com"
            },
            {
                "id": 9,
                "caption": "hello3 world",
                "status": "UNLIKED",
                "email": "hell244444@gmail.com"
            },
            {
                "id": 10,
                "caption": "hello3 world",
                "status": "UNLIKED",
                "email": "hell2444444@gmail.com"
            },
            {
                "id": 11,
                "caption": "hello3 world",
                "status": "UNLIKED",
                "email": "hell24444444@gmail.com"
            },
            {
                "id": 1,
                "caption": "hello world",
                "status": "LIKED",
                "email": "hello@gmail.com"
            }
        ]
    }
}
```

### Creating a post

To create a post we have to send a POST request to the server at http://localhost:3001/api/v1/posts/create with the a request body
for example in my case:

```json
{
    "caption": "hello3 world",
    "email": "hell24444444@gmail.com"
}
```

You get the following response

```json
{
    "timeStamp": "2021-09-25T13:13:36.7135392",
    "statusCode": 201,
    "status": "CREATED",
    "limit": 0,
    "page": 0,
    "posts": {
        "posts": {
            "id": 11,
            "caption": "hello3 world",
            "status": "UNLIKED",
            "email": "hell24444444@gmail.com"
        }
    }
}
```

### Updating a post
To update a Post we send a PUT request to the server either at http://localhost:3001/api/v1/posts/react/1 where ``1`` is the post id
this will update the status and you will get the following response:

```json
{
    "timeStamp": "2021-09-25T13:15:30.6320753",
    "statusCode": 200,
    "status": "OK",
    "limit": 0,
    "page": 0,
    "posts": {
        "posts": {
            "id": 1,
            "caption": "hello world",
            "status": "LIKED",
            "email": "hello@gmail.com"
        }
    }
}
```
We can also update the post itself by hitting the http://localhost:3001/api/v1/posts/update/1 and pass the json body to 
update the whole post for example in my case if i send a PUT request with the following json body:

```json
{
    "caption": "yauuk world",
    "email": "4@gmail.com"
}
```
I will get the following response:

```json

{
    "timeStamp": "2021-09-25T14:35:02.6082878",
    "statusCode": 200,
    "status": "OK",
    "limit": 0,
    "page": 0,
    "posts": {
        "posts": {
            "id": 1,
            "caption": "yauuk world",
            "status": "LIKED",
            "email": "4@gmail.com"
        }
    },
    "error": {
        "field": "id",
        "message": "the post of that id does not exists"
    }
}
```

### Deleting a post

To delete a post we send a DELETE request to http://localhost:3001/api/v1/posts/delete/1 where ``1`` is the id of the post 
that we want to delete. We will get the following response on success deletion of the post:

```json
{
  "timeStamp": "2021-09-25T14:36:40.5841222",
  "statusCode": 200,
  "status": "OK",
  "limit": 0,
  "page": 0,
  "posts": {
    "posts": true
  }
}
```
### Conclusion
The API is bugging when it faces some errors for example if you look at this response:

```json

{
    "timeStamp": "2021-09-25T14:35:02.6082878",
    "statusCode": 200,
    "status": "OK",
    "limit": 0,
    "page": 0,
    "posts": {
        "posts": {
            "id": 1,
            "caption": "yauuk world",
            "status": "LIKED",
            "email": "4@gmail.com"
        }
    },
    "error": {
        "field": "id",
        "message": "the post of that id does not exists"
    }
}
```
We are getting errors where there are no errors. We will have a practical on Error Handling using JPA in this series.

### Configuring cores

This snipped is already configured for us all we have is to cpy it and edit to work on our application and it is found
in our ``PaginationApplication.java``  and looks as follows:

```java
//PaginationApplication.java


@Bean
public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:3002"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
        "Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
        "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
        "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Filename"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
}
```
Now with these conditions we will be able to make API request to this server from the application that is not running on 
port ``3001``. The following line does the configuration of origins:

```java
corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:3002"));
```
This means that applications that are listening on port 3000 and 3002 will be able to 
listen to make api request to our server.

