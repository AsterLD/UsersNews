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

    @GetMapping
    public String showArticleListPage(@RequestParam(defaultValue = "1") int page, Model model) {
        return articleService.showArticleListByIsApproved(true, model, "article/articleList", page);
    }

    @GetMapping("/search")
    public String searchResultArticlesList(@RequestParam (defaultValue = "") String search,
                                           @RequestParam(defaultValue = "1") int page, Model model) {
        return articleService.showArticleListSearchByTitle(search, true, model, page);
    }

    @GetMapping("/user/{username}/")
    public String showUserArticleListPage(@RequestParam(defaultValue = "1") int page,
                                          @PathVariable String username, Model model) {
        return articleService.showUserArticleList(username, model, page);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','EDITOR')")
    @GetMapping("/unapproved")
    public String showUnapprovedArticleList(@RequestParam(defaultValue = "1") int page, Model model) {
        return articleService.showArticleListByIsApproved(false, model, "article/unapprovedArticleList", page);
    }

    @GetMapping("/{articleId}")
    public String showArticlePage(@PathVariable Long articleId, Model model) {
        return articleService.showArticleById(articleId, model);
    }

    @GetMapping("/new")
    public String showCreateArticlePage() {
        return "article/createArticlePage";
    }

    @PostMapping
    public String addArticle(@RequestParam("file") MultipartFile file, Article article) {
        return articleService.addArticle(file, article, "redirect:/");
    }

    @GetMapping("/{articleId}/edit")
    public String showEditArticlePage(@PathVariable Long articleId, Model model) {
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