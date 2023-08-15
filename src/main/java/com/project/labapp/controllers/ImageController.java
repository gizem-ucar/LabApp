package com.project.labapp.controllers;

import com.project.labapp.entities.Report;
import com.project.labapp.entities.User;
import com.project.labapp.repos.ReportRepository;
import com.project.labapp.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{reportId}")
    public ResponseEntity<String> getImage(@PathVariable Long reportId) {
        // Veritabanından ilgili raporu bulun
        Optional<Report> optionalReport = reportRepository.findById(reportId);
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            byte[] reportImage = report.getReportImage();

            // Eğer raporun görseli boş değilse, görseli base64 kodlayarak döndürün
            if (reportImage != null) {
                String base64Image = Base64.getEncoder().encodeToString(reportImage);
                return ResponseEntity.ok().body(base64Image);
            }
        }

        // Eğer rapor veya raporun görseli yoksa, 404 Not Found döndürün
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<String> getUserImage(@PathVariable Long userId) {
        // Veritabanından ilgili userı bulun
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            byte[] userImage = user.getUserImage();

            // Eğer userImage görseli boş değilse, görseli base64 kodlayarak döndürün
            if (userImage != null) {
                String base64Image = Base64.getEncoder().encodeToString(userImage);
                return ResponseEntity.ok().body(base64Image);
            }else {
                // Eğer userImage görseli boşsa, 404 Not Found döndürün
                return ResponseEntity.notFound().build();
            }
        }else {
            // Kullanıcı bulunamadıysa, 404 Not Found döndürün
            return ResponseEntity.notFound().build();
        }
    }
}
