package com.ld.usersnews.repos;

import com.ld.usersnews.models.User;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UserRepo extends CrudRepository <User, Long> {
    User findUserByUsername(String username);
    List<User> findAll();
    List<User> findUserByUsernameContains(String username);
}
