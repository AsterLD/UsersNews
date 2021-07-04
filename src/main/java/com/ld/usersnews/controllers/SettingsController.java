package com.ld.usersnews.controllers;

import com.ld.usersnews.Service.PasswordService;
import com.ld.usersnews.Service.UserService;
import com.ld.usersnews.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users/{username}/settings")
public class SettingsController {

    private final PasswordService passwordService;
    private final UserService userService;

    public SettingsController(PasswordService passwordService, UserService userService) {
        this.passwordService = passwordService;
        this.userService = userService;
    }

    @GetMapping()
    public String showSettingsMenuPage (@PathVariable String username, Model model) {
        return "settings/settingsMenuPage";
    }

    @GetMapping("/change_name")
    public String showChangeUsernamePage (@PathVariable String username, Model model) {
        return userService.findUser( model, username, "settings/changeUsernamePage");
    }

    @PostMapping("/change_name")
    public String changeUsername (Model model, String newUsername, String password,
                                  User user) {
        return userService.changeUsername(model, newUsername, password, user);
    }

    @GetMapping("/password")
    public String showChangePasswordViaOldPasswordPage (@PathVariable String username, Model model) {
        return passwordService.showChangeUserPasswordPage(model, username);
    }

    @PostMapping("/password")
    public String changePasswordViaOldPassword (@PathVariable String username,
                                                @RequestParam String oldPassword,
                                                @RequestParam String newPassword,
                                                Model model) {
        return passwordService.changeUserPassword(model, username, oldPassword, newPassword);
    }

    @GetMapping("/security_question")
    public String showChangeSecurityQuestionAndSecurityAnswerPage (@PathVariable String username, Model model) {
        return "settings/changeSecurityQuestionPage";
    }

    @PostMapping("/security_question")
    public String changeSecurityQuestionAndSecurityAnswer (@PathVariable String username,
                                                           @RequestParam String newSecurityQuestion,
                                                           @RequestParam String newSecurityAnswer,
                                                           @RequestParam String password, Model model) {
        return userService.changeSecurityQuestionAndAnswer(model, username, newSecurityQuestion, newSecurityAnswer, password);
    }

}
