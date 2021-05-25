package com.ld.usersnews.Service;
import com.ld.usersnews.models.User;
import com.ld.usersnews.repos.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class PasswordService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    public PasswordService(PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    public String resetUserPassword(Model model, String username, String action) {
        if (userRepo.findUserByUsername(username) != null) {
            if (action.equals("reset")) {
                return "password/reset";
            }
            if (action.equals("securityQuestion")) {
                model.addAttribute("user", userRepo.findUserByUsername(username));
                return "password/securityQuestion";
            }
        }
        return "password/choiceRecoveryMethod";
    }

    public String setNewPasswordViaSecurityQuestion(Model model, String username, String answer, String password) {
        User user = userRepo.findUserByUsername(username);
        model.addAttribute("user", userRepo.findUserByUsername(username));
        if (passwordEncoder.matches(answer, user.getSecurityAnswer())) {
            user.setPassword(passwordEncoder.encode(password));
            userRepo.save(user);
            return "redirect:/login";
        }
        return "password/securityQuestion";
    }
}
