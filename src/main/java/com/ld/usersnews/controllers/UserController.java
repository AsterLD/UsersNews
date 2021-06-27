package com.ld.usersnews.controllers;

import com.ld.usersnews.Service.UserService;
import com.ld.usersnews.models.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUserListPage(@RequestParam(defaultValue = "1") int page, Model model) {
        return userService.findAll(model, page);
    }

    @GetMapping("/search")
    public String findUsers(@RequestParam(name = "search", defaultValue = "") String search,
                            @RequestParam(defaultValue = "1") int page, Model model) {
        return userService.searchUsers(search, model, page);
    }

    @GetMapping("/{username}")
    public String showUserInfoPage(@RequestParam(defaultValue = "1") int page,
                                   @PathVariable String username, Model model) {
        return userService.findUserInfo(username, model, "user/userInfoPage", page);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{username}/edit")
    public String showEditUserPage(@PathVariable String username, Model model) {
        return userService.findUser(username, model, "user/editUserPage");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{username}/edit")
    public String updateUser(@RequestParam String username,
                             @RequestParam Map<String, String> form,
                             @RequestParam("userId") User user,
                             Model model) {
        return userService.editUser(username, form, user, model);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{username}")
    public String deleteUser (@PathVariable String username) {
        return userService.deleteUser(username);
    }
}
