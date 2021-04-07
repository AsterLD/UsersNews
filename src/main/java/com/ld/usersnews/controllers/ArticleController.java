package com.ld.usersnews.controllers;

import com.ld.usersnews.Service.ArticleService;
import com.ld.usersnews.models.Article;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping()
    public String allArticlesList(Model model) {
        return articleService.showArticleListByIsApproved(true, model, "article/articleList");
    }

    @GetMapping("/search")
    public String searchResultArticlesList(String search, Model model) {
        return articleService.showSearchArticleList(search, true, model);
    }

    @GetMapping("/unapproved")
    public String unapprovedArticleList(Model model) {
        return articleService.showArticleListByIsApproved(false, model, "article/unapprovedArticles");
    }

    @GetMapping("/{articleId}")
    public String showArticle(@PathVariable Long articleId, Model model) {
        return articleService.showArticleById(articleId, model);
    }

    @GetMapping("/new")
    public String createArticle() {
        return "article/newArticle";
    }

    @PostMapping
    public String addArticle(@RequestParam("file") MultipartFile file, Article article) {
        return articleService.addArticle(file, article, "redirect:");
    }

    @GetMapping("/{articleId}/edit")
    public String editArticle(@PathVariable Long articleId, Model model) {
        return articleService.showArticleToEdit(articleId, model);
    }

    @PatchMapping("/{articleId}")
    public String updateArticle(@PathVariable Long articleId, Article editedArticle){
        return articleService.editArticle(articleId, editedArticle);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','EDITOR')")
    @PatchMapping("/{articleId}/approve")
    public String approveArticle(@PathVariable Long articleId){
        return articleService.approveArticle(articleId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','EDITOR')")
    @DeleteMapping("/{articleId}")
    public String deleteArticle(@PathVariable Long articleId) {
        return articleService.deleteArticleById(articleId);
    }

    @PatchMapping("/{articleId}/img")
    public String editArticleImage(@RequestParam(value = "file", required = false) MultipartFile file,
                                   @PathVariable Long articleId){
        return articleService.editArticleImage(file, articleId);
    }
}