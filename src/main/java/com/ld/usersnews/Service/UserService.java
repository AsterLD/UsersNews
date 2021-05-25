package com.ld.usersnews.Service;

import com.ld.usersnews.models.Role;
import com.ld.usersnews.models.User;
import com.ld.usersnews.repos.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    public UserService(PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
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

    public String findAll(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "user/usersList";
    }

    public String findUser(String username, Model model, String page) {
        model.addAttribute("user", userRepo.findUserByUsername(username));
        return page;
    }

    public String searchUsers(String username, Model model) {
        model.addAttribute("users", userRepo.findUserByUsernameContains(username));
        return "user/usersList";
    }

    public String editUser(String username, Map<String, String> form, User user, Model model) {
        User userFromDb = userRepo.findUserByUsername(username);
        if (userFromDb != null && !user.getUserId().equals(userFromDb.getUserId())) {
            model.addAttribute("message", "Пользователь с таким именем уже существует!");
            model.addAttribute("user", user);
            return "user/editUser";
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
