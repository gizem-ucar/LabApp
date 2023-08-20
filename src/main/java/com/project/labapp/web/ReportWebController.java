package com.project.labapp.web;

import com.project.labapp.entities.Report;
import com.project.labapp.requests.ReportCreateRequest;
import com.project.labapp.requests.ReportUpdateRequest;
import com.project.labapp.responses.AuthResponse;
import com.project.labapp.responses.ReportResponse;
import com.project.labapp.services.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
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

    @GetMapping("/web/reportUpdate/{reportId}")
    public String getReportUpdate(@PathVariable Long reportId, Model model) {
        ReportResponse report = reportService.getOneReportById(reportId);
        if (report != null) {
            model.addAttribute("report", report);
            return "reportUpdate"; // Thymeleaf şablon adını dikkate alarak düzenleyin
        } else {
            // Handle report not found
            return "errorPage"; // Örnek olarak hata sayfasına yönlendirme
        }
    }

    @GetMapping("/web/createReport/{patientId}")
    public String getReportAdd(@PathVariable Long patientId, Model model) {
        model.addAttribute("patientId", patientId);
        return "reportAdd";
    }

    @GetMapping("/web/reports/{reportId}")
    public String showReportDetail(@PathVariable Long reportId, Model model) {
        ReportResponse report = reportService.getOneReportById(reportId);

        if (report != null) {
            byte[] reportImage = report.getReportImage();
            if (reportImage != null) {
                String base64Image = Base64.getEncoder().encodeToString(reportImage);
                model.addAttribute("base64Image", base64Image);
                model.addAttribute("report", report);
                return "reportDetail";
            }
            model.addAttribute("report", report);
            return "reportDetail"; // Thymeleaf şablon adını dikkate alarak düzenleyin
        } else {
            // Handle report not found
            return "errorPage"; // Örnek olarak hata sayfasına yönlendirme
        }
    }


    @PostMapping(consumes = "multipart/form-data",path = "/web/reportUpdate/{reportId}")
    public String updateOneReport(@PathVariable Long reportId,
                                  /*@RequestParam("file") MultipartFile reportImageFile, */
                                  @Valid @ModelAttribute ReportUpdateRequest updateReport,
                                  Model model)throws IOException {
        /*updateReport.setReportImageFile(reportImageFile);*/
        Report report = reportService.updateOneReportById(reportId, updateReport);
        model.addAttribute("report",report);
        return "redirect:/web/reports/{reportId}";
    }

    @PostMapping(consumes = "multipart/form-data", path = "/web/createReport/{patientId}")
    public String createOneReport (
            /*@RequestParam("file") MultipartFile reportImageFile,*/
            HttpSession session,
            @Valid @ModelAttribute ReportCreateRequest newReportRequest,
            @PathVariable Long patientId,
            Model model
    ) throws IOException {
        /*newReportRequest.setReportImageFile(reportImageFile);*/

        AuthResponse authResponse = (AuthResponse) session.getAttribute("authResponse");

        if (authResponse != null) {
            Long userId = authResponse.getUserId();
            newReportRequest.setUserId(userId);
            newReportRequest.setPatientId(patientId);
        }

        Date reportDate = newReportRequest.getReportDate();
        newReportRequest.setReportDate(reportDate);

        Report createdReport = reportService.createOneReport(newReportRequest);
        model.addAttribute("createdReport",createdReport);

        if (createdReport != null) {
            return "redirect:/web/reports";
        } else {
            return "errorPage";
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

    @PostMapping("/web/reportDelete/{reportId}")
    public String deleteOneReport(@PathVariable Long reportId, Model model){
        reportService.deleteOneReportById(reportId);
        return "redirect:/web/reports";
    }


}
