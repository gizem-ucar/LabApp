package com.project.labapp.web;

import com.project.labapp.entities.Report;
import com.project.labapp.repos.ReportRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Base64;
import java.util.Optional;

@Controller
public class ImageUtils {

    ReportRepository reportRepository;

    public ImageUtils(ReportRepository reportRepository){
        this.reportRepository = reportRepository;
    }
    public String getBase64Image(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    @GetMapping("web/images/{reportId}")
    public String getImage(@PathVariable Long reportId , Model model) {
        // Veritabanından ilgili raporu bulun
        Optional<Report> optionalReport = reportRepository.findById(reportId);
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            byte[] reportImage = report.getReportImage();

            // Eğer raporun görseli boş değilse, görseli base64 kodlayarak döndürün
            if (reportImage != null) {
                String base64Image = Base64.getEncoder().encodeToString(reportImage);
                model.addAttribute("base64Image", base64Image);
                return "reportDetail";
            }
        }

        // Eğer rapor veya raporun görseli yoksa, 404 Not Found döndürün
        return "errorPage";
    }
}