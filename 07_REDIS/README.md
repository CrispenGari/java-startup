### Redis and Java

In this one we are going to have a look at how to integrate Redis and Java to perform some basic redis
commands using Java as a programming language.

<p align="center"><img src="https://docs.redis.com/latest/images/icon_logo/logo-redis-3.svg" width="100%"/></p>

### Requirements

* You need to have Redis installed in your computer.
* You should also make sure that you have `Java` installed in your computer as well.
* You need a code editor, in my case i will be using `intellij` but feel free to use whatever editor you want


### Project setup

If you are using `intellij` create a new project and select a `marven` project, intellij will generate the
project folders for you.

### Lettuce
> Lettuce is a thread-safe Redis client that supports both synchronous and asynchronous connections.

We are going to edit the `pom.xml` file and add the following dependecy so that we will be able to connect to
redis using lettuce.

```xml
<dependency>
    <groupId>biz.paluch.redis</groupId>
    <artifactId>lettuce</artifactId>
    <version>3.2.Final</version>
</dependency>
```
### Testing the connections
To test the connection we are going to create a `RedisApplication` class in the `java` folder of the main
and add the following code to it.

```java
// java/RedisApplication.java

import com.lambdaworks.redis.*;

public class RedisApplication {
    public static void main(String[] args) {
        // redis connection string
        String connectionString = "redis://@localhost:6379";
        RedisClient client = new RedisClient(RedisURI.create(connectionString));
        try {
            RedisConnection<String, String> connection = client.connect();
            System.out.println("connected to redis server");
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
```

If everything went well we will be able to see the following message in the console:

```shell
connected to redis server
```

Otherwise we will see an error.

### Executing Redis commands

### 1. `ping()`

To get the `PONG` message from redis to test if everything is going well we can do it as follows:

```java
RedisConnection<String, String> connection = client.connect();
System.out.println(connection.ping());
connection.close();
```
> output: "PONG"

### 2. Storing name in the redis database and getting it.
To store the name in the redis database we use the `set` method.

> Note that `REDIS` stores data as `key` and `values` pairs like hashmaps.

```java
connection.set("name", "crispen");
```
### 3. getting the name that we have store in the redis database

We use the `get` method and pass the `key` to get the value.
```java
String name = connection.get("name");
System.out.printf("The name is: %s", name); // crispen
```

### 4. Deleting the name
We are going to use the `del` method and pass the `key` of the data that we want to delete in our case we want to delete the `key` "name"

```java
connection.del("name");
```

### 5. `expire`
We can set the expiration time in `seconds` of a `key`. The `expire` method is used to set the expiry of a key.

```java
connection.expire("name", 2);
```
The above command will set a key to expire in `2seconds`

### 6. `setex`
Stores a data as a key and value as well as the `expiration` time in seconds

```java
connection.setex("name",  2, "crispen");
```
The key `name` will expire in `2seconds` so if we try to use the `connection.get("name")` method we will get `null`


### 7. `exists`
This method checks if the key exists or not, it returns a boolan value:


```java
boolean exist = connection.exists("name");
```

You can find all commands in the Redis documentation, or you can find the
summary of them at [www.tutorialspoint.com](https://www.tutorialspoint.com/redis/redis_keys.htm).


### Storing a `client`

Let's say we have a `Client` class which looks as follows:

```java
public class Client {
    private String name;
    private int age;

    public Client(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
```
We have to implement `toString()` method that will serialise our Client object since redis only stores string values.
```java
public class Client {
   ....
    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

```

Then in the `RedisApplication` we will save our client as follows:

```java
Client client1 = new Client("crispen", 22);
connection.set("client1", client1.toString());
System.out.println(connection.get("client1"));
```

### Refs

1. [docs.redis.com](https://docs.redis.com/latest/rs/references/client_references/client_jav)
2. [www.tutorialspoint.com](https://www.tutorialspoint.com/redis/index.htm)
