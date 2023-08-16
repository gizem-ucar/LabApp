package com.project.labapp.web;

import com.project.labapp.responses.ReportResponse;
import com.project.labapp.services.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {


    @GetMapping("/web/home")
    public String home(Model model) {
        // Model verilerini hazırlama
        model.addAttribute("message", "Merhaba Dünya!");

        // Thymeleaf şablonunu döndürme
        return "home";
    }

    /*@GetMapping("/web/profile")
    public String profile(Model model) {
        // Model verilerini hazırlama
        model.addAttribute("message", "Merhaba Dünya!");

        // Thymeleaf şablonunu döndürme
        return "profile";
    }*/
    /*@GetMapping("/web/reports/{reportId}")
    public String reportDetail(Model model) {
        // Model verilerini hazırlama
        model.addAttribute("message", "Merhaba Dünya!");

        // Thymeleaf şablonunu döndürme
        return "reportDetail";
    }*/
    @GetMapping("/web/reportUpdate")
    public String reportUpdate(Model model) {
        // Model verilerini hazırlama
        model.addAttribute("message", "Merhaba Dünya!");

        // Thymeleaf şablonunu döndürme
        return "reportDetail";
    }

}
