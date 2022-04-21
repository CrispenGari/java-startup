### Getting started with Thymeleaf


In this one we are going to have a quick overview of the [`Thymeleaf`](https://www.thymeleaf.org) template engine.
### Spring Boot Dev Tools
To enable spring boot `dev-tools` we have to make sure that the `spring-boot-devtools` dependency is in the `pom.xml`

Read more here:
1. [Stackoverflow](https://stackoverflow.com/questions/33349456/how-to-make-auto-reload-with-spring-boot-on-idea-intellij)
2. [www.codejava.net](https://www.codejava.net/frameworks/spring-boot/spring-boot-automatic-restart-using-spring-boot-devtools)


```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

Next if you are on intellij verify that the option check-box `File`->`Setting` –> `Build, Execution, Deployment` –> `Compiler`–>**Build project automatically** is selected.


> Then, press `SHIFT+CTRL+A` for `Linux/Windows` users or `Command+SHIFT+A` for `Mac` users,
then type registry in the opened pop-up window. Scroll down to `Registry`... using the down arrow key and hit `ENTER` on Registry....
In the `Registry` window verify the option **compiler.automake.allow.when.app.running** is checked.

* If the static files are not reloaded, press `CTRL+F9` to force a reload.

> Note :: For who those not found that option in registry.The newer version of intellij idea for my case @Version:2021.2 the compiler.automake.allow.when.app.running option is moved to advanced settings:

With only that the server will be able to restart it self on save, on file delete, etc.

### Initializing the project
This project was initialized with 2 dependencies which are:

1. spring web
2. thymeleaf

To get the exact startup for this application you can get it [here](https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.5.6&packaging=jar&jvmVersion=17&groupId=com.thymeleaf&artifactId=thymeleaf&name=thymeleaf&description=web%20application%20using%20thymeleaf&packageName=com.thymeleaf.thymeleaf&dependencies=web,thymeleaf)
. This project was initialized using the [spring initializr](https://start.spring.io).


### Hello world.

We are going to show how we can render basic html pages in the web-browser. First we need to create package called customers in this example. This package will be responsible
for all the code that will be responsible for the customers pages. We are going to have two pages that we will
render the home and the about. So we need to create a controller called `CustomerController` and it's code will look as follows:

```java
package com.thymeleaf.thymeleaf.customers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {
    @GetMapping("/")
    public String home(Model model){
        return  "home";
    }

    @GetMapping("/about")
    public  String about(Model model){
        return "about/index";
    }

}
```
We are going to annotate our `CustomerController` with `@Controller` and create 2 routes the one that will render
the `home.html` located in the ``resources/templates`` and looks as follows:

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Home</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<h1>Home</h1>
</body>
</html>
```
The following route is the `/about` which will render the `index.html` which is located in the `resources/templates/about` folder and looks as follows:

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>About</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<h1>About</h1>
</body>
</html>
```

Now if you start the server we will be able to see html pages that will be rendered as per route.


### Static files

Static files include files like:
1. css
2. javascript
3. images
4. e.t.c

Next we are going to show how we can style make our templates work with static files like `javascript`, `css` and `images`. We will go to the `resources/static` file and create the following folders:

1. css - will contain all the css files for templates
2. js - will contain all the javascript files for templates
3. images - will contain all the images for our web app

We are going to create the `home.css` in the `css` folder of the static folder and we will add the following code in it:
```css
* {
    margin: 0;
    padding: 0;
    background: #f5f5f5;
}

img{
width: 100%;
object-fit: contain;
}
```

We are going to create the `home.js` in the `js` folder of the static folder and we will add the following code in it:

```js
console.log("Hello, from home.js")

const hello = ()=>{
    alert("hello, from home.js")
}
```
We are then going to add an image in the images folder named `image.jpg`.

Go to the templates folder and edit the `home.html` to look as follows:

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Home</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" type="text/css" th:href="@{/css/home.css}" />
</head>
<body>
<h1>Home</h1>
<img th:src="@{/images/image.jpg}">
<button onclick="hello()">
   alert
</button>
<script th:src="@{/js/home.js}"></script>
</body>
</html>
```

Now when we start the application, we will be able to see a webpage with an image and a little bit of styling
and a button which is running a javascript es6 function when clicked.

### The template Syntax.

1. `th:text`
* used to render text on html tags for example if we have the following `GetMapping` in the `CustomerController`
it takes in the model and add an attribute on the model `welcome` as follows:

```java
  @GetMapping("/")
    public String home(Model model){
        model.addAttribute("welcome", "welcome to the home page.");
        return  "home";
    }
```

In the `home.html` we will render the text as follows:

```html
<h1 th:text="'hello' + ${welcome} + ' here you go'"></h1>
```

### Rendering data from an object.
Suppose we have a `Customer` type which has two properties age and name. And we want to sent that data to
the template engine. We can have something of the following nature in the `Controller` and `html` files:

```java
class Customer{
    private String name;
    private Long age;

    public Customer(String name, Long age){
        this.name = name;
        this.age = age;
    }
    // getters and setters here
}
@Controller
public class CustomerController {
    @GetMapping("/")
    public String home(Model model){
        Customer customer = new Customer(
                "customer1",
                45L
        );
        model.addAttribute("customer", customer);
        return  "home";
    }
    @GetMapping("/about")
    public  String about(Model model){
        return "about/index";
    }

}
```

`home.html`:

```html
<p th:text="'name: ' + ${customer.name}"></p>
<p th:text="'age: ' + ${customer.age}"></p>
```

### Collection Attributes

Let's say we have a list of customers that need to be displayed on a table. In the controller we may have
the code that may look as follows.

```java
 @GetMapping("/")
    public String home(Model model){
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("customer1", 45L));
        customers.add(new Customer("customer2", 45L));
        model.addAttribute("customers", customers);
        return  "home";
    }
```

`home.html`

```html
<tr th:each ="customer: ${customers}">
    <td th:text="${customer.name}"></td>
    <td th:text="${customer.age}"></td>
</tr>
```

### Conditional Evaluation
The `th:if=”${condition}”` attribute is used to display a section of the view if the condition is met.
The `th:unless=”${condition}”` attribute is used to display a section of the view if the condition is not met.

The `Controller` will be changed to:

```java
@GetMapping("/")
    public String home(Model model){
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("customer1", 45L, true));
        customers.add(new Customer("customer2", 45L, false));
        model.addAttribute("customers", customers);
        return  "home";
}
```


`home.html`

```html
 <tr th:each ="customer: ${customers}">
        <td th:text="${customer.name}"></td>
        <td th:text="${customer.age}"></td>
        <td>
            <span th:if="${customer.admin} == true">ADMIN</span>
            <span th:unless="${customer.admin} == true">NOT ADMIN</span>
        </td>
    </tr>
```

### switch and case

The th:switch and `th:case` attributes
 are used to display content conditionally using the switch statement structure.

For this we are going to change our `html` part to use switch case instead of if unless as follows:

```html
 <tr th:each ="customer: ${customers}">
        <td th:text="${customer.name}"></td>
        <td th:text="${customer.age}"></td>
        <td th:switch="${customer.admin}">
            <span th:case="true" th:text="'ADMIN'"></span>
            <span th:case="false" th:text="'NOT ADMIN'"></span>
        </td>
    </tr>
```

### Handling User Input

We are going to create a simple form that will take the data from the form. First we are going to create the
User class in  the `user` package as follows.

Before creating the User type we need to add `lombok` in our `pom.xml` so that we will use annotations
to generate setters and getters.

```xml
 <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <optional>true</optional>
    </dependency>
```
Restart the `pom.xml` and we will then go and create a User type as follows in the user package



```java
package com.thymeleaf.thymeleaf.user;
import lombok.*;
import java.sql.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private String email;
    private String password;
    private String gender;
    private String note;
    private boolean married;
    private Date birthday;
    private String profession;
}
```

Next we are going to create a Controller in the user package named `UserController` as follows:

```java
@Controller
public class UserController {
    @GetMapping("/register")
    public String showForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        List<String> listProfession = Arrays.asList("Developer", "Tester", "Architect");
        model.addAttribute("listProfession", listProfession);
        return "user/register";
    }

    @PostMapping("/register")
    public String submitForm(@ModelAttribute("user") User user) {
        System.out.println(user);
        return "user/home";
    }
}
```

We are going to then create two templates the `home.html` and the `register.html` and they will be looking as folllows:


1. `register.html`


```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="ISO-8859-1">
    <title>User Registration</title>
</head>
<body>
<div align="center">
    <h1>User Registration</h1>
    <form action="#" th:action="@{/register}" method="post" th:object="${user}">
        <label>Full name:</label>
        <input type="text" th:field="*{name}" /><br/>

        <label>E-mail:</label>
        <input type="text" th:field="*{email}" /><br/>

        <label>Password:</label>
        <input type="password" th:field="*{password}" /><br/>

        <label>Birthday (yyyy-mm-dd):</label>
        <input type="text" th:field="*{birthday}" /><br/>

        <label>Gender:</label>
        <input type="radio" th:field="*{gender}" value="Male" />Male
        <input type="radio" th:field="*{gender}" value="Female" />Female<br/>

        <label>Profession:</label>
        <select th:field="*{profession}">
            <option th:each="p : ${listProfession}" th:value="${p}" th:text="${p}" />
        </select>
        <br/>

        <label>Married?</label>
        <input type="checkbox" th:field="*{married}" /><br/>

        <label>Note:</label>
        <textarea rows="5" cols="25" th:field="*{note}"></textarea>
        <br/>

        <button type="submit">Register</button>
    </form>
</div>
</body>
</html>
```

First we have the following line in our html file:

```html
<form action="#" th:action="@{/register}" method="post" th:object="${user}">
```
When i submit the form, we are going to hit the `/register` route as a post method with the user object as
data in the model. In Thymeleaf, hyperlink is wrapped inside `@{}` and access a model object inside `${}`.
Note that in this form tag, the `th:object` attribute points to the name of the model object sent from Spring MVC controller.

```html
<input type="text" th:field="*{name}" />
```
The `th:field` attribute points to the field name of the object in the model. Field name is wrapped inside `*{}`

2. `home.html`
The `home.html` will be used to display the data that comes from the submitted form and it looks as follows:

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="ISO-8859-1">
    <title>Registration Success</title>
    <style type="text/css">
    span {
        display: inline-block;
        width: 200px;
        text-align: left;
    }
</style>
</head>
<body>
<div align="center">
    <h2>Registration Succeeded!</h2>
    <span>Full name:</span><span th:text="${user.name}"></span><br/>
    <span>E-mail:</span><span th:text="${user.email}"></span><br/>
    <span>Password:</span><span th:text="${user.password}"></span><br/>
    <span>Gender:</span><span th:text="${user.gender}"></span><br/>
    <span>Profession:</span><span th:text="${user.profession}"></span><br/>
    <span>Married?</span><span th:text="${user.married}"></span><br/>
    <span>Note:</span><span th:text="${user.note}"></span><br/>
</div>
</body>
```

That s how we can handle form data in spring boot and thymeleaf.

### Refs

* [www.baeldung.com](https://www.baeldung.com/thymeleaf-in-spring-mvc)
* [www.codejava.net](https://www.codejava.net/frameworks/spring-boot/spring-boot-thymeleaf-form-handling-tutorial)