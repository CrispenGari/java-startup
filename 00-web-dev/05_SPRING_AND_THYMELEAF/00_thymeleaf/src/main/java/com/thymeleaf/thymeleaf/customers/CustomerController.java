package com.thymeleaf.thymeleaf.customers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
class Customer implements Serializable {
    private String name;
    private Long age;
    private Boolean admin;
}
@Controller
public class CustomerController {
    @GetMapping()
    public String home( Model model){
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
