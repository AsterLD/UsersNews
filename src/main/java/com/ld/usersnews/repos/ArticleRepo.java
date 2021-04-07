package com.ld.usersnews.repos;

import com.ld.usersnews.models.Article;
import com.ld.usersnews.models.User;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ArticleRepo extends CrudRepository<Article, Long> {
    Article findArticleByArticleId(Long articleId);
    List<Article> findArticlesByIsApprovedOrderByArticleDateDesc(boolean isApproved);
    List<Article> findArticlesByUser(User user);
    List<Article> findArticlesByTitleContainsAndIsApprovedOrderByArticleDateDesc(String title, boolean isApproved);
}
