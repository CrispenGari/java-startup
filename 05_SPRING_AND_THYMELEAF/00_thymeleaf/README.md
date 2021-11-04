### Getting started with Thymeleaf

In this one we are going to have a quick overview of the [`Thymeleaf`](https://www.thymeleaf.org) template engine.

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

