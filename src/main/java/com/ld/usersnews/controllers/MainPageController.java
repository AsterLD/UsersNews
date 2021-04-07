package com.ld.usersnews.controllers;

import com.ld.usersnews.Service.ArticleService;
import com.ld.usersnews.models.Article;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainPageController {

    private final ArticleService articleService;

    public MainPageController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String ShowUserFeed(Article article, Model model) {
        return articleService.showArticleListByIsApproved(true, model, "home");
    }
}
