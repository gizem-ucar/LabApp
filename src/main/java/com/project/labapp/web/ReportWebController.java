package com.project.labapp.web;

import com.project.labapp.entities.Report;
import com.project.labapp.responses.AuthResponse;
import com.project.labapp.responses.ReportResponse;
import com.project.labapp.security.JwtUserDetails;
import com.project.labapp.services.ReportService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    /*@GetMapping("/web/myReports")
    public String showMyReports(Model model) {
        // Giriş yapan kullanıcının kimlik bilgilerini al
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kimlik bilgilerinden kullanıcının id bilgisini al
        Long userId = ((JwtUserDetails) authentication.getPrincipal()).getId();

        // Kullanıcının raporlarını al ve model nesnesine ekle
        List<ReportResponse> reports = reportService.getAllReports(Optional.of(userId), Optional.empty());
        model.addAttribute("reports", reports);

        return "myReports"; // my-reports.html ismindeki HTML dosyası
    } */


}
