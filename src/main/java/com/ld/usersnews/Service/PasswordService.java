package com.ld.usersnews.Service;
import com.ld.usersnews.models.User;
import com.ld.usersnews.repos.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/*
 * Класс PasswordService, хранит в себе логику, для работы PasswordController.
 * showChangeUserPasswordPage - Отображает страницу изменения пароля,
 * changeUserPassword - Если старый пароль был введен верно, изменяет пароль учетной записи в БД,
 * recoveryUserPassword - Отображает страницу восстановления пароля, в зависимости от выбранного способа,
 * setNewPasswordViaSecurityQuestion - Если ответ на секретный вопрос был введен верно, изменяет пароль учетной записи.
 */

@Service
public class PasswordService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    public PasswordService(PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    public String showChangeUserPasswordPage(Model model, String username) {
        model.addAttribute("user", userRepo.findUserByUsername(username));
        return "settings/changePasswordPage";
    }

    public String changeUserPassword(Model model,
                                     String username,
                                     String oldPassword,
                                     String newPassword) {
        User user = userRepo.findUserByUsername(username);
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepo.save(user);
            return "redirect:/logout";
        }
        else {
            model.addAttribute("message", "Неверный пароль!");
            model.addAttribute("user", userRepo.findUserByUsername(username));
            return "settings/changePasswordPage";
        }
    }

    public String recoveryUserPassword(Model model, String username, String action) {
        if (userRepo.findUserByUsername(username) != null) {
            if (action.equals("reset")) {
                return "password/resetViaEmailPage";
            }
            if (action.equals("securityQuestion")) {
                model.addAttribute("user", userRepo.findUserByUsername(username));
                return "password/securityQuestionPage";
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
        return "password/securityQuestionPage";
    }
}
