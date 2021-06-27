package com.ld.usersnews.repos;

import com.ld.usersnews.models.Article;
import com.ld.usersnews.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepo extends PagingAndSortingRepository<User, Long> {
    User findUserByUserId(Long userId);
    User findUserByUsername(String username);
    Page<User> findAll(Pageable pageable);
    Page<User> findUserByUsernameContains(String username, Pageable pageable);
}
