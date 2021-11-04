package com.thymeleaf.thymeleaf.customers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("welcome", "welcome to the home page.");
        return  "home";
    }

    @GetMapping("/about")
    public  String about(Model model){
        return "about/index";
    }

}
