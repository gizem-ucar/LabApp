package com.project.labapp.web;

import com.project.labapp.entities.User;
import com.project.labapp.requests.UserUpdateRequest;
import com.project.labapp.responses.AuthResponse;
import com.project.labapp.responses.ReportResponse;
import com.project.labapp.services.ReportService;
import com.project.labapp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class UserWebController {

    private UserService userService;

    private ReportService reportService;

    public UserWebController(UserService userService, ReportService reportService){
        this.userService = userService;
        this.reportService = reportService;
    }


    @GetMapping("/web/profile")
    public String getOneUser(HttpSession session, Model model){
        // HttpSession üzerinden authResponse objesini almak
        AuthResponse authResponse = (AuthResponse) session.getAttribute("authResponse");

        // AuthResponse objesi boş değilse modelde taşı
        if (authResponse != null) {
            Long userId = authResponse.getUserId();
            User user = userService.getOneUserById(userId);
            byte[] userImage = user.getUserImage();
            if (userImage != null){
                String base64Image = Base64.getEncoder().encodeToString(userImage);
                model.addAttribute("base64Image",base64Image);
            }

            List<ReportResponse> reports = reportService.getAllReports(Optional.of(userId), Optional.empty());
            model.addAttribute("reports", reports);
            model.addAttribute("user", user);
            return "profile";
        } else {
            // AuthResponse objesi boşsa gerekli hata işlemleri yapılabilir
            return "Auth/login";
        }
    }

    @GetMapping("/web/profileUpdate/{userId}")
    public String getProfileUpdate(@PathVariable Long userId, HttpSession session, Model model) {
        AuthResponse authResponse = (AuthResponse) session.getAttribute("authResponse");

        if (authResponse != null) {
            model.addAttribute("userId",userId);
            return "profileUpdate";
        }
        return "Auth/login";
    }

    @PostMapping(consumes = "multipart/form-data",path ="/web/profileUpdate/{userId}")
    public String updateOneUser(@PathVariable Long userId,
                                @Valid @ModelAttribute UserUpdateRequest newUser,
                                Model model) throws IOException {
        User user = userService.updateOneUser(userId, newUser);
        model.addAttribute("user",user);
        return "redirect:/web/profile";
    }
    @DeleteMapping("/web/users/{userId}")
    public void deleteOneUser(@PathVariable Long userId) {
        userService.deleteById(userId);
    }
}
