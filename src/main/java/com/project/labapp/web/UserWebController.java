package com.project.labapp.web;

import com.project.labapp.entities.User;
import com.project.labapp.requests.UserUpdateRequest;
import com.project.labapp.responses.AuthResponse;
import com.project.labapp.responses.ReportResponse;
import com.project.labapp.services.ReportService;
import com.project.labapp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @GetMapping("/web/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/web/users")
    public User createUser(@RequestBody User newUser){
        return userService.saveOneUser(newUser);
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
            String base64Image = Base64.getEncoder().encodeToString(userImage);
            model.addAttribute("base64Image",base64Image);

            List<ReportResponse> reports = reportService.getAllReports(Optional.of(userId), Optional.empty());
            model.addAttribute("reports", reports);
            model.addAttribute("user", user);
            return "profile";
        } else {
            // AuthResponse objesi boşsa gerekli hata işlemleri yapılabilir
            return "errorPage";
        }
    }

    @PutMapping("/web/users/{userId}")
    public User updateOneUser(@PathVariable Long userId, @RequestBody UserUpdateRequest newUser) throws IOException {
        return userService.updateOneUser(userId, newUser);
    }

    @DeleteMapping("/web/users/{userId}")
    public void deleteOneUser(@PathVariable Long userId) {
        userService.deleteById(userId);
    }
}
