package com.project.labapp.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {

    @GetMapping("/web/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication, HttpSession session) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        // Diğer session verilerini temizleme
        session.removeAttribute("authResponse");

        return "redirect:/web/login?logout"; // Çıkış sonrası yönlendirme
    }
}