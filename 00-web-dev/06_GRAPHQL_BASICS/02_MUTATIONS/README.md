### Mutation

In this one we are going to have a look on how we can create a simple mutation.
We are going to create simple 3 mutations:

1. register
2. login
3. logout

mutations. So we will go in the `resources` folder and create a folder `graphql` and create a file
called `Mutation.graphqls` and it will look as follows:

```graphql
type Mutation{
    register(username: String, password: String): AuthResponse
    login(username: String, password: String): AuthResponse
    logout: Boolean
}
```
The `AuthResponse` looks as follows:

```
# auth/AuthResponse.graphqls
type AuthResponse{
    error: Error
    user: User
}
```

The `Error` looks as follows:

```
# auth/Error.graphqls
type Error{
    field: String!
    message: String!
}
```
Finally the `User` looks as follows:

```
# auth/User.graphqls
type User{
    username: String!
    password: String!
}
```

Now we can go ahead and create `resolvers` our resolvers will be in the `resolvers` package. In this package
we are going to create a package called `Auth` and this package will have our `AuthResolver.java` class which
will contain all our authentication mutations and it will look as follows:

```java
@Component
public class AuthResolver implements GraphQLMutationResolver {

    public AuthResponse login(String username, String password){
        if(username.length() < 3){
            ErrorType error = ErrorType.builder()
                    .field("username").message("username must be at least 3 chars.").build();
            return AuthResponse.builder().error(error).build();
        }
        if(password.length() < 3){
            ErrorType error = ErrorType.builder()
                    .field("password").message("password must be at least 3 chars.").build();
            return AuthResponse.builder().error(error).build();
        }

        User user = User.builder().username(username).password(password).build();
        return AuthResponse.builder().user(user).build();
    }

    public AuthResponse register(String username, String password){
        if(username.length() < 3){
            ErrorType error = ErrorType.builder()
                    .field("username").message("username must be at least 3 chars.").build();
            return AuthResponse.builder().error(error).build();
        }
        if(password.length() < 3){
            ErrorType error = ErrorType.builder()
                    .field("password").message("password must be at least 3 chars.").build();
            return AuthResponse.builder().error(error).build();
        }

        User user = User.builder().username(username).password(password).build();
        return AuthResponse.builder().user(user).build();
    }
    public Boolean logout(){
        return true;
    }
}
```
> Note that we are not committing anything from the database in this example, we are just making the graphql
api communicate with java spring-boot application.

### AuthResponse
`AuthResponse` is a java class which looks as follows:
```java
@Value
@Builder
public class AuthResponse {
    private User user;
    private ErrorType error;
}
```

### User
User is also a java class which looks as follows:

```java
@Value
@Builder
public class User {
    private  String username;
    private String password;
}
```

### ErrorType
ErrorType is also a java class which contain two fields and message and it looks as follows

```java
@Value
@Builder
public class ErrorType {
    private String field;
    private String message;
}
```

Now if we run our application, we will be able to make the following mutation.

1. register/login
The register and login mutations looks the same so we can send a mutation to the server which looks as follows:

```
mutation Login($username: String!, $password: String!){
    login(username: $username, password: $password){
        error{
            message
            field
        }
        user{
            username
            password
        }
    }
}
```

With the following variables:

```json
{
   "username": "",
   "password": ""
}
```
We will get the following response:

```json
{
    "data": {
        "login": {
            "error": {
                "message": "username must be at least 3 chars.",
                "field": "username"
            },
            "user": null
        }
    }
}
```

Or you can make a register mutation as follows:

```
mutation Register($username: String!, $password: String!){
    register(username: $username, password: $password){
        error{
            message
            field
        }
        user{
            username
            password
        }
    }
}
```

With the following variables

```json
{
   "username": "username",
   "password": "password"
}
```
We will get the following response:

```json
{
    "data": {
        "register": {
            "error": null,
            "user": {
                "username": "username",
                "password": "password"
            }
        }
    }
}
```

### Logout mutation
If run the following `logout` mutation
```
mutation Logout{
    logout
}
```

With no query variables we will get the following response:

```json
{
    "data": {
        "logout": true
    }
}
```

> _Next we are going to have a look at `GraphQLResolver`._