package com.project.labapp.controllers;

import com.project.labapp.entities.Report;
import com.project.labapp.repos.ReportRepository;
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
}
