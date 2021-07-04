package com.ld.usersnews.controllers;

import com.ld.usersnews.Service.CommentService;
import com.ld.usersnews.models.Comment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/users/{username}/comments")
    public String showUserCommentListPage(@RequestParam(defaultValue = "1") int page, @PathVariable String username, Model model) {
        return commentService.showCommentListByUsername( model, username, page);
    }

    @PostMapping("/articles/{articleId}/comments")
    public String saveComment(@PathVariable Long articleId, Comment comment) {
        return commentService.saveComment(articleId, comment);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','EDITOR')")
    @DeleteMapping("/articles/{articleId}/comments/{commentId}")
    public String deleteComment(@PathVariable Long articleId, @PathVariable Long commentId) {
        return commentService.deleteCommentById(commentId);
    }
}
