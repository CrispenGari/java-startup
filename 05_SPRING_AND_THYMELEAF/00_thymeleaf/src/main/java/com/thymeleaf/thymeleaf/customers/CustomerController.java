package com.thymeleaf.thymeleaf.customers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Customer implements Serializable {
    private String name;
    private Long age;
    private Boolean admin;

    public Customer(String name, Long age, Boolean admin) {
        this.name = name;
        this.age = age;
        this.admin = admin;
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

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", admin=" + admin +
                '}';
    }
}
@Controller
public class CustomerController {
    @GetMapping("/")
    public String home(Model model){
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("customer1", 45L, true));
        customers.add(new Customer("customer2", 45L, false));
        model.addAttribute("customers", customers);
        return  "home";
    }
    @GetMapping("/about")
    public  String about(Model model){
        return "about/index";
    }

}
