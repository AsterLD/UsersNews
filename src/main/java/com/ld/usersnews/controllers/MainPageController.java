package com.ld.usersnews.controllers;

import com.ld.usersnews.Service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class MainPageController {

    private final ArticleService articleService;

    public MainPageController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String showMainPage(@RequestParam(defaultValue = "1") int page, Model model) {
        return articleService.showArticleListByIsApproved (true, model, "homePage", page);
    }
}
