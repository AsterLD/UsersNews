package com.ld.usersnews.controllers;

import com.ld.usersnews.Service.PasswordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/password")
public class PasswordController {

    private final PasswordService passwordService;

    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @GetMapping()
    public String showRecoveryMethodPage() {
        return "password/recoveryMethodPage";
    }

    @PostMapping("/reset_password")
    public String showRestoreMethodPage(@RequestParam String username,
                                        @RequestParam String action, Model model) {
        return passwordService.recoveryUserPassword(model, username, action);
    }

    @PostMapping("/new")
    public String changePassword (@RequestParam String username,
                                  @RequestParam String answer,
                                  @RequestParam String password, Model model) {
        return passwordService.setNewPasswordViaSecurityQuestion(model, username, answer, password);
    }
}
