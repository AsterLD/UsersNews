package com.ld.usersnews.Service;

import com.ld.usersnews.models.Comment;
import com.ld.usersnews.models.Role;
import com.ld.usersnews.models.User;
import com.ld.usersnews.repos.ArticleRepo;
import com.ld.usersnews.repos.CommentRepo;
import com.ld.usersnews.repos.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import static com.ld.usersnews.util.PageListGenerator.generateAvailablePageList;

@Service
public class CommentService {

    private final CommentRepo commentRepo;
    private final UserRepo userRepo;
    private final ArticleRepo articleRepo;

    public CommentService(CommentRepo commentRepo, UserRepo userRepo, ArticleRepo articleRepo) {
        this.commentRepo = commentRepo;
        this.userRepo = userRepo;
        this.articleRepo = articleRepo;
    }

    public String showCommentListByUsername(Model model, String username, int pageNumber) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (username.equals(SecurityContextHolder.getContext().getAuthentication().getName()) ||
                authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Role.ADMIN.toString()))) {
            User user = userRepo.findUserByUsername(username);
            Page<Comment> contentPage = commentRepo.findCommentsByUser(user, PageRequest.of(pageNumber - 1, 5));
            generateAvailablePageList(model, pageNumber, contentPage);
            model.addAttribute("username", username);
            return "comment/userCommentListPage";
        }
        return "redirect:/";
    }

    public String saveComment(Long articleId, Comment comment) {
        comment.setArticle(articleRepo.findArticleByArticleId(articleId));
        comment.setUser(userRepo.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        commentRepo.save(comment);
        return "redirect:/articles/{articleId}";
    }

    public String  deleteCommentById(Long commentId) {
        commentRepo.deleteById(commentId);
        return "redirect:/articles/{articleId}";
    }
}
