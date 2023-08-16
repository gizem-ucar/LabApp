package com.project.labapp.web;

import com.project.labapp.responses.AuthResponse;
import com.project.labapp.responses.ReportResponse;
import com.project.labapp.services.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class ReportWebController {

    ReportService reportService;

    public ReportWebController(ReportService reportService){
        this.reportService = reportService;
    }

    @GetMapping("/web/reports")
    public String reports(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> patientId, Model model) {
        List<ReportResponse> reports = reportService.getAllReports(userId, patientId);
        model.addAttribute("reports", reports);
        if (userId.isPresent() && patientId.isPresent()){
            return "reports";
        }else if (userId.isPresent()){
            return "myReports";
        }else if(patientId.isPresent()){
            return "reports";
        }else
            return "reports";
    }

    @GetMapping("/web/reports/{reportId}")
    public String showReportDetail(@PathVariable Long reportId, Model model) {
        ReportResponse report = reportService.getOneReportById(reportId);
        if (report != null) {
            model.addAttribute("report", report);
            return "reportDetail"; // Thymeleaf şablon adını dikkate alarak düzenleyin
        } else {
            // Handle report not found
            return "errorPage"; // Örnek olarak hata sayfasına yönlendirme
        }
    }


    @GetMapping("/web/myReports")
    public String showMyReports(HttpSession session, Model model) {
        // HttpSession üzerinden authResponse objesini almak
        AuthResponse authResponse = (AuthResponse) session.getAttribute("authResponse");

        // AuthResponse objesi boş değilse modelde taşı
        if (authResponse != null) {
            Long userId = authResponse.getUserId();
            // Kullanıcının raporlarını al ve model nesnesine ekle
            List<ReportResponse> reports = reportService.getAllReports(Optional.of(userId), Optional.empty());
            model.addAttribute("reports", reports);
            return "myReports";
        } else {
            // AuthResponse objesi boşsa gerekli hata işlemleri yapılabilir
            return "errorPage";
        }
    }


}
