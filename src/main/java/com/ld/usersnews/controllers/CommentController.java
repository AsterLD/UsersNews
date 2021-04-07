package com.ld.usersnews.controllers;

import com.ld.usersnews.Service.CommentService;
import com.ld.usersnews.models.Comment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/articles")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{articleId}/comments")
    public String saveComment(@PathVariable Long articleId, Comment comment) {
        return commentService.saveComment(comment, articleId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','EDITOR')")
    @DeleteMapping("/{articleId}/comments/{commentId}")
    public String deleteComment(@PathVariable Long articleId, @PathVariable Long commentId) {
        return commentService.deleteCommentById(commentId);
    }
}
