package com.ld.usersnews.repos;

import com.ld.usersnews.models.Comment;
import com.ld.usersnews.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepo extends PagingAndSortingRepository<Comment, Long> {
    Page<Comment> findCommentsByUser(User user, Pageable pageable);
}
