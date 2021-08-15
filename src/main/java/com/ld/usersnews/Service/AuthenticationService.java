package com.ld.usersnews.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Класс AuthenticationService, хранит в себе логику, для работы AuthenticationController.
 * logout - в случае, если пользователь ранее вошел в систему, метод, используя SecurityContextLogoutHandler,
 * выполняет выход из системы, и пребрасывает на главную страницу.
 */

@Service
public class AuthenticationService {
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
}
