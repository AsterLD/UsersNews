package com.ld.usersnews.controllers;

import com.ld.usersnews.Service.PasswordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/password")
public class PasswordController {

    private final PasswordService passwordService;

    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @GetMapping()
    public String forgotPasswordPage () {
        return "password/choiceRecoveryMethod";
    }

    @PostMapping("/reset_password")
    public String resetPasswordPage (Model model, @RequestParam String username, @RequestParam String action) {
        return passwordService.resetUserPassword(model, username, action);
    }

    @PostMapping("/new")
    public String changePasswordPage (Model model,
                                      @RequestParam String username,
                                      @RequestParam String answer,
                                      @RequestParam String password) {
        return passwordService.setNewPasswordViaSecurityQuestion(model, username, answer, password);
    }
}
