package com.ld.usersnews.controllers;

import com.ld.usersnews.Service.UserService;
import com.ld.usersnews.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String createNewUser() {
        return "registration/registrationPage";
    }

    @PostMapping
    public String addUser(Model model, User user) {
        return userService.addUser(model, user);
    }
}
