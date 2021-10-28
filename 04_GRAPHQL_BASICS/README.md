### GraphQL Spring data JPA and MySQL

In this one we are going to have a look at how we are going to intergrate ``spring-data-jpa``, `mysql` and `graphql`.


### Project initialization

To initialize the project we are going to use [start.spring.io](https://start.spring.io/) to add dependencies to our
project. The `pom.xml` will have the following dependencies:


```xml
	<dependencies>
    		<dependency>
    			<groupId>org.springframework.boot</groupId>
    			<artifactId>spring-boot-starter-data-jpa</artifactId>
    		</dependency>
    		<dependency>
    			<groupId>org.springframework.boot</groupId>
    			<artifactId>spring-boot-starter-web</artifactId>
    		</dependency>

    		<dependency>
    			<groupId>mysql</groupId>
    			<artifactId>mysql-connector-java</artifactId>
    			<scope>runtime</scope>
    		</dependency>
    		<dependency>
    			<groupId>org.projectlombok</groupId>
    			<artifactId>lombok</artifactId>
    			<optional>true</optional>
    		</dependency>
    		<dependency>
    			<groupId>com.graphql-java</groupId>
    			<artifactId>graphiql-spring-boot-starter</artifactId>
    			<version>3.10.0</version>
    		</dependency>

    		<dependency>
    			<groupId>com.graphql-java</groupId>
    			<artifactId>graphql-spring-boot-starter</artifactId>
    			<version>3.10.0</version>
    		</dependency>
    		<dependency>
    			<groupId>com.graphql-java</groupId>
    			<artifactId>graphql-java-tools</artifactId>
    			<version>4.3.0</version>
    		</dependency>

    		<dependency>
    			<groupId>javax.xml.bind</groupId>
    			<artifactId>jaxb-api</artifactId>
    			<version>2.3.0</version>
    		</dependency>
    		<dependency>
    			<groupId>org.springframework.boot</groupId>
    			<artifactId>spring-boot-starter-test</artifactId>
    			<scope>test</scope>
    		</dependency>
    	</dependencies>

```

Next we are going to go to the `application.yml` and add the following properties to it:

```yml
# src/main/resources/application.yml
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
    url: jdbc:mysql://localhost:3306/social
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver

server:
  port: 3001

graphql:
  servlet:
    mapping: /graphql
    enabled: true
    exception-handlers-enabled: true
    context-setting: per_query_with_instrumentation
    tracingEnabled: false

graphiql:
  mapping: /graphiql
  endpoint:
    graphql: /graphql
    subscriptions: /subscriptions
  subscriptions:
    reconnect: false
    timeout: 30
  enabled: true
  pageTitle: GraphiQL
  cdn:
    enabled: false
    version: 0.13.0
```

Next we are going to create two models each model will be in it's own package:

### 1. User

The user entity will be looking as follows:
```java
@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends AuditModel implements Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    @Column(nullable = false, unique = true)
    private  String username;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "user")
    @Fetch(value= FetchMode.SELECT)
    private Collection<Post> posts;
}

```

### 2. Post
The post entity will be looking as follows

```java

@Entity
@Table(name="posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post extends AuditModel implements Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    @Column(nullable = false, unique = true)
    private  String caption;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;
}
```

There's a one to many relationship between the user and posts. This means that the user can have many
posts and and many post can belong to a single user.

> Note that all these entities extends the `AuditModel` which allow us to have columns like `created_at` and `updated_at` when
we change data in the database. This `AuditModel`  looks as follows in the `common` package:

```java
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

In order for this to work we have to go into the main class in our case is the `GraphqlApplication.java` and add the `@EnableJpaAuditing`
annotation as follows:


```java
@SpringBootApplication
@EnableJpaAuditing
public class GraphqlApplication {
	public static void main(String[] args) {
		SpringApplication.run(GraphqlApplication.class, args);
	}
}
```

### Create GraphQL Schema
All the graphql schemas should have the `.graphqls` extentions and we are going to add them in the `resources` folder. We are going
to have 3 schemas which are:

0. Types (`types.graphqls`)

```graphql
type Post {
    id: Int!
    caption: String!
    user: User
}

type User{
    id: Int!
    username: Int!
    posts: [Post]!
}
# Root
type Query {
}

# Root
type Mutation {
}
```
1. User (`user.graphqls`)

```graphqls
extend type Query {
    findAllUsers: [User]!
}
extend type Mutation {
    createUser(username: String!): User!
}
```

2. Post (`post.graphqls`)


```graphqls
extend type Query {
    findAllPosts: [Post]!
}
extend type Mutation {
    createPost(caption: String!, userId: Int): Post!
}
```

### Creating Repositories

1. UserRepository

The user repository will be located in the `user` package and it will be looking as follows

```java
package com.example.graphql.user;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Long> {
}
```

2. PostRepository
```java
package com.example.graphql.post;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PostRepository extends JpaRepository<Post, Long> {
}
```

The post repository will be located in the `post` package and it will be looking as follows



http://localhost:3001/graphiql

### Refs

0. [github.com/eh3rrera](https://github.com/eh3rrera/graphql-java-spring-boot-example/blob/master/pom.xml)
1. [www.bezkoder.com/](https://www.bezkoder.com/spring-boot-graphql-mysql-jpa/)
2. [github.com/mesuk](https://github.com/mesuk/GraphQlSpringIntegration)
3. [betterprogramming.pub](https://betterprogramming.pub/an-advanced-guide-to-graphql-with-java-mysql-and-jpa-implementation-83f791a1f676)