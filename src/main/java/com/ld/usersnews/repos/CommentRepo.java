package com.ld.usersnews.repos;

import com.ld.usersnews.models.Comment;
import com.ld.usersnews.models.User;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CommentRepo extends CrudRepository<Comment, Long> {
    List<Comment> findCommentsByUser(User user);
}
