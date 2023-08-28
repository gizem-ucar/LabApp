package com.project.labapp.web;

import com.project.labapp.responses.AuthResponse;
import com.project.labapp.responses.ReportResponse;
import com.project.labapp.services.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {


    @GetMapping("/web/home")
    public String home(HttpSession session) {
        AuthResponse authResponse = (AuthResponse) session.getAttribute("authResponse");

        if (authResponse != null) {
            return "home";
        }
        return "Auth/login";
    }
}
