### GraphQL and Java Hello world

In this one we are going to setup the environment and create a hello world program in java-graphql.

### Requirements
Make sure that you have `java` in my case i have version `17` installed on my computer. I'm using `intellij` as
my `IDE` and `Postman` as my `Client`.

### Creating a new project
Create a marven project using [start.spring.io](https://start.spring.io/) and select the following dependencies

1. lombok
2. spring-web

Or you can click [this link](https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.6.1&packaging=jar&jvmVersion=11&groupId=com.example&artifactId=graphql&name=graphql&description=Demo%20project%20for%20Spring%20Boot&packageName=com.example.graphql&dependencies=web,lombok) to an download the zip file with all dependencies that we will begin with.

> _Unzip the folder and open it in intellij._


Next we are going to add the `graphql` core dependency in our `pom.xml` file:

```xml
<dependency>
  <groupId>com.graphql-java-kickstart</groupId>
  <artifactId>graphql-spring-boot-starter</artifactId>
  <version>12.0.0</version>
</dependency>
```

You can find this dependency on their [github repository](https://github.com/graphql-java-kickstart/graphql-spring-boot)
Reload marven and open the `application.yml` file and change the server port number to be `3001`

```yml
server:
  port: 3001
```
My `pom.xml` dependencies will look as follows:

```xml
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>com.graphql-java-kickstart</groupId>
			<artifactId>graphql-spring-boot-starter</artifactId>
			<version>12.0.0</version>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>
```
### Creating a Simple Schema
All graphql files will be in the `resources/graphql` folder. Our first graphql type in the `schema` is the `Query` type.
We are going to create a file called `Query.graphqls` which will look as follows:

```graphql
type Query{
    hello(name: String): String!
}
```
Now our query `hello` takes a name as a string variable and returns a string.

> _Note that all the graphql files will be in this `graphql`, and we can create more nested directories in this directory and be able to reference all the types in the graphql folder_

### Creating a Resolver
A resolver is essentially a way our graphql schema get the data. We are going in the `java` folder and create
a package called `resolver`. In that package we are going to create a class called `HelloResolver` which
will be decorated with `@Component` and implements a graphql query resolver interface as follows:

```java
package com.example.graphql.resolvers;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
public class HelloResolver implements GraphQLQueryResolver {
    public String hello(String name){
        return "Hello, " + name;
    }
}
```

With this bare minimum of code we can try to make our graphql query `hello` and get the results back using
a graphql playground, graphiql or any other client. In our case we are going to use `Postman` client.

### Postman Client for GraphQL

1. Go to postman and enter the url to your graphql server in our case it's http://localhost:33001/graphql.
2. Change the request method to `POST`
3. Under request body select `GraphQL`
4. Write the following query

```
query Hello($name: String){
    hello(name: $name)
}
```

5. Add the following to graphql variables

```json
{
    "name": "world"
}
```
6. Click `SEND` and you will get the following response from our graphql server.

```json
{
    "data": {
        "hello": "Hello, user"
    }
}
```

Now we have our hello world program in graphql and java working.

### Refs
1. [graphql-spring-boot](https://github.com/graphql-java-kickstart/graphql-spring-boot)
2. [stackoverflow.com](https://stackoverflow.com/questions/66127334/graphql-java-is-expecting-query-parameter)


