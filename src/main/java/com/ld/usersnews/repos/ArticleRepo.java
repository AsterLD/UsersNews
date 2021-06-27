package com.ld.usersnews.repos;

import com.ld.usersnews.models.Article;
import com.ld.usersnews.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ArticleRepo extends PagingAndSortingRepository<Article, Long> {
    Article findArticleByArticleId(Long articleId);
    Page<Article>findArticlesByUser(User user, Pageable pageable);
    Page<Article> findArticlesByIsApprovedOrderByArticleDateDesc(boolean isApproved, Pageable pageable);
    Page<Article> findArticlesByUserOrderByArticleDateDesc(User user, Pageable pageable);
    Page<Article> findArticlesByTitleContainsAndIsApprovedOrderByArticleDateDesc(String title, boolean isApproved, Pageable pageable);
}
