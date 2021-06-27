package com.ld.usersnews.Service;

import com.ld.usersnews.models.Role;
import com.ld.usersnews.models.User;
import com.ld.usersnews.repos.ArticleRepo;
import com.ld.usersnews.repos.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.*;
import java.util.stream.Collectors;

import static com.ld.usersnews.util.PageListGenerator.generateAvailablePageList;

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final ArticleRepo articleRepo;

    public UserService(PasswordEncoder passwordEncoder, UserRepo userRepo, ArticleRepo articleRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.articleRepo = articleRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    public String addUser(User user, Model model) {
        User userFromDb = userRepo.findUserByUsername(user.getUsername());
        if (userFromDb != null) {
            model.addAttribute("message", "Пользователь уже существует!");
            return "registration/newUser";
        }
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpire(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSecurityAnswer(passwordEncoder.encode(user.getSecurityAnswer()));
        userRepo.save(user);
        return "redirect:/login";
    }

    public String findAll(Model model, int pageNumber) {
        Page<User> usersPage = userRepo.findAll(PageRequest.of(pageNumber - 1, 5));
        generateAvailablePageList(model, pageNumber, usersPage);
        return "user/userList";
    }

    public String findUser(String username, Model model, String page) {
        model.addAttribute("user", userRepo.findUserByUsername(username));
        return page;
    }

    public String findUserInfo(String username, Model model, String page, int pageNumber) {
        User user = userRepo.findUserByUsername(username);
        generateAvailablePageList(model, pageNumber, articleRepo.findArticlesByUser(user, PageRequest.of(pageNumber - 1, 5)));
        model.addAttribute("user", user);
        return page;
    }

    public String searchUsers(String search, Model model, int pageNumber) {
        Page<User> usersPage = userRepo.findUserByUsernameContains(search, PageRequest.of(pageNumber - 1, 5));
        generateAvailablePageList(model, pageNumber, usersPage);
        model.addAttribute("search", search);
        return "user/userList";
    }

    public String changeUsername(String username, String password, User user, Model model) {
        User userFromDb = userRepo.findUserByUsername(username);
        User renamedUser = userRepo.findUserByUserId(user.getUserId());
        if (userFromDb != null && !renamedUser.getUserId().equals(userFromDb.getUserId())) {
            model.addAttribute("message", "Пользователь с таким именем уже существует!");
            model.addAttribute("user", user);
            return "settings/changeUsername";
        }
        if (!passwordEncoder.matches(password, renamedUser.getPassword())) {
            model.addAttribute("message", "Неверный пароль!");
            model.addAttribute("user", user);
            return "settings/changeUsername";
        }
        renamedUser.setUsername(username);
        userRepo.save(renamedUser);
        return "redirect:/logout";
    }

    public String changeSecurityQuestionAndAnswer(String username,
                                                  String newSecurityQuestion,
                                                  String newSecurityAnswer,
                                                  String password, Model model) {
        User user = userRepo.findUserByUsername(username);
        if (passwordEncoder.matches(password, user.getPassword())) {
            user.setSecurityQuestion(newSecurityQuestion);
            user.setSecurityAnswer(passwordEncoder.encode(newSecurityAnswer));
            userRepo.save(user);
            return "redirect:/logout";
        }
        else {
            model.addAttribute("message", "Неверный пароль!");
            return "settings/changeSecurityQuestion";
        }
    }

    public String editUser(String username, Map<String, String> form, User user, Model model) {
        User userFromDb = userRepo.findUserByUsername(username);
        if (userFromDb != null && !user.getUserId().equals(userFromDb.getUserId())) {
            model.addAttribute("message", "Пользователь с таким именем уже существует!");
            model.addAttribute("user", user);
            return "user/editUserPage";
        }
        user.setEnabled(form.containsKey("isEnabled"));
        user.setUsername(username);
        user.getRoles().clear();
        Set<String> userRoles = form.keySet();
        userRoles.retainAll(Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet()));
        userRoles.forEach(s -> user.getRoles().add(Role.valueOf(s)));
        userRepo.save(user);
        return "redirect:/users";
    }

    public String deleteUser (String username) {
        User user = userRepo.findUserByUsername(username);
        if (user != null) {
            userRepo.delete(user);
        }
        return "redirect:/users";
    }
}
