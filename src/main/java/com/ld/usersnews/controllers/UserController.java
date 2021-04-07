package com.ld.usersnews.controllers;

import com.ld.usersnews.Service.ArticleService;
import com.ld.usersnews.Service.CommentService;
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

    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;

    public UserController(ArticleService articleService, CommentService commentService, UserService userService) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping
    public String showAllUsers(Model model) {
        return userService.findAll(model);
    }

    @GetMapping("/search")
    public String findUsers(@RequestParam(name = "search", defaultValue = "") String search, Model model) {
        return userService.searchUsers(search, model);
    }

    @GetMapping("/{username}")
    public String userInfo(@PathVariable String username, Model model) {
        return userService.findUser(username, model, "user/userInfo");
    }

    @GetMapping("/{username}/comments")
    public String usersCommentsByUsername(@PathVariable String username, Model model) {
        return commentService.findCommentListByUsername(username, model);
    }

    @GetMapping("/{username}/articles")
    public String usersArticlesByUsername (@PathVariable String username, Model model) {
        return articleService.showUserArticleList(username, model);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{username}/edit")
    public String editUser(@PathVariable String username, Model model) {
        return userService.findUser(username, model, "user/editUser");
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
