package com.ld.usersnews.Service;

import com.ld.usersnews.models.Article;
import com.ld.usersnews.models.Role;
import com.ld.usersnews.models.User;
import com.ld.usersnews.repos.ArticleRepo;
import com.ld.usersnews.repos.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.ld.usersnews.models.Role.AUTHORIZED_AUTHOR;

@Service
public class ArticleService  {

    @Value("${upload.path}")
    private String uploadPath;

    private final ArticleRepo articleRepo;
    private final UserRepo userRepo;

    public ArticleService(ArticleRepo articleRepo, UserRepo userRepo) {
        this.articleRepo = articleRepo;
        this.userRepo = userRepo;
    }

    public String showArticleById(Long articleId, Model model) {
        model.addAttribute("article", articleRepo.findArticleByArticleId(articleId));
        return "article/articlePage";
    }

    public String showArticleListByIsApproved (boolean isApproved, Model model, String page) {
        model.addAttribute("articleList", articleRepo.findArticlesByIsApprovedOrderByArticleDateDesc(isApproved));
        return page;
    }

    public String showSearchArticleList (String searchTitle, boolean isApproved, Model model) {
        model.addAttribute("articleList", articleRepo.findArticlesByTitleContainsAndIsApprovedOrderByArticleDateDesc(searchTitle, isApproved) );
        return "article/articleList";
    }

    public String showUserArticleList (String username, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (username.equals(SecurityContextHolder.getContext().getAuthentication().getName()) ||
                authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Role.ADMIN.toString()))) {
            model.addAttribute("articleList", articleRepo.findArticlesByUser(userRepo.findUserByUsername(username)));
            return "article/userArticleList";
        }
        return "redirect:/";
    }

    public String showArticleToEdit (Long articleId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Article article = articleRepo.findArticleByArticleId(articleId);
        if (article.getUser().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())
                || authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Role.EDITOR.toString()))) {
            model.addAttribute("article", article);
            return "article/editArticle";
        }
        return "redirect:/";
    }

    public String editArticle (Long articleId, Article editedArticle) {
        User user = userRepo.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Article originalArticle = articleRepo.findArticleByArticleId(articleId);
        originalArticle.setTitle(editedArticle.getTitle());
        originalArticle.setArticleText(editedArticle.getArticleText());
        if(user.getRoles().contains(AUTHORIZED_AUTHOR)) {
            originalArticle.setIsApproved(true);
        }
        articleRepo.save(originalArticle);
        return "redirect:/";
    }

    public String editArticleImage(MultipartFile file, Long articleId){
        Article article = articleRepo.findArticleByArticleId(articleId);
        if(file != null) {
            saveImageFile(file, article);
        }
        articleRepo.save(article);
        return "redirect:/articles/{articleId}/edit";
    }

    public String approveArticle(Long articleId) {
        Article article = articleRepo.findArticleByArticleId(articleId);
        article.setIsApproved(true);
        articleRepo.save(article);
        return "redirect:/articles/unapproved";
    }

    public String deleteArticleById (Long articleId) {
        articleRepo.deleteById(articleId);
        return "redirect:/articles/unapproved";
    }

    public String addArticle (MultipartFile file, Article article, String page) {
        User user = userRepo.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user.getRoles().contains(AUTHORIZED_AUTHOR)) {
            article.setIsApproved(true);
        }
        if(file != null) {
            saveImageFile(file, article);
        }
        article.setUser(user);
        articleRepo.save(article);
        return page;
    }

    private void saveImageFile(MultipartFile file, Article article){
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()){
            uploadDir.mkdir();
        }
        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + file.getOriginalFilename();
        try {
            file.transferTo(new File(uploadPath + "/" + resultFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        article.setArticleImageName(resultFilename);
    }
}