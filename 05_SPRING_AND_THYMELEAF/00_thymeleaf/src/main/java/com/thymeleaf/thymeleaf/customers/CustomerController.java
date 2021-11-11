package com.thymeleaf.thymeleaf.customers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

class Customer{
    private String name;
    private Long age;

    public Customer(String name, Long age){
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Long getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
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
