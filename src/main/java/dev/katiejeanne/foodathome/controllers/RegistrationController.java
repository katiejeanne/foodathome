package dev.katiejeanne.foodathome.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @GetMapping("/register")
    public String getRegistrationPage() {
        return "register";
    }


}
