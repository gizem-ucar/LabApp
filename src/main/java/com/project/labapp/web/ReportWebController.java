package com.project.labapp.web;

import com.project.labapp.config.ReportMapper;
import com.project.labapp.entities.Report;
import com.project.labapp.requests.ReportCreateRequest;
import com.project.labapp.requests.ReportUpdateRequest;
import com.project.labapp.responses.AuthResponse;
import com.project.labapp.responses.ReportResponse;
import com.project.labapp.services.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    ReportMapper reportMapper;

    public ReportWebController(ReportService reportService, ReportMapper reportMapper){
        this.reportService = reportService;
        this.reportMapper = reportMapper;
    }

    @GetMapping("/web/reports")
    public String reports(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> patientId, HttpSession session, Model model) {

        AuthResponse authResponse = (AuthResponse) session.getAttribute("authResponse");

        if (authResponse != null) {
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
        }else {
            return "Auth/login";
        }


    }

    @GetMapping("/web/reportUpdate/{reportId}")
    public String getReportUpdate(@PathVariable Long reportId, HttpSession session, Model model) {
        AuthResponse authResponse = (AuthResponse) session.getAttribute("authResponse");

        if (authResponse != null) {
            ReportResponse report = reportService.getOneReportById(reportId);
            if (report != null) {
                model.addAttribute("report", report);
                return "reportUpdate"; // Thymeleaf şablon adını dikkate alarak düzenleyin
            } else {
                // Handle report not found
                return "errorPage"; // Örnek olarak hata sayfasına yönlendirme
            }
        }
        return "Auth/login";
    }

    @GetMapping("/web/createReport/{patientId}")
    public String getReportAdd(@PathVariable Long patientId, HttpSession session, Model model) {
        AuthResponse authResponse = (AuthResponse) session.getAttribute("authResponse");

        if (authResponse != null) {
            model.addAttribute("patientId", patientId);
            return "reportAdd";
        }
        return "Auth/login";
    }

    @GetMapping("/web/reports/{reportId}")
    public String showReportDetail(@PathVariable Long reportId, HttpSession session, Model model) {
        AuthResponse authResponse = (AuthResponse) session.getAttribute("authResponse");

        if (authResponse != null) {
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
        return "Auth/login";
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
            return "Auth/login";
        }
    }

    @PostMapping("/web/reportDelete/{reportId}")
    public String deleteOneReport(@PathVariable Long reportId, Model model){
        reportService.deleteOneReportById(reportId);
        return "redirect:/web/reports";
    }

    @GetMapping("/web/reports/search")
    public String searchReports(@RequestParam(required = false) String patientFirstName,
                                                              @RequestParam(required = false) String patientLastName,
                                                              @RequestParam(required = false) String patientTC,
                                                              @RequestParam(required = false) String userFirstName,
                                                              @RequestParam(required = false) String userLastName,
                                                              Model model) {
        List<ReportResponse> reportResponses = reportService.searchReports(patientFirstName, patientLastName, patientTC, userFirstName, userLastName);
        model.addAttribute("reports", reportResponses);
        return "reports";
    }

    @GetMapping("/web/reports/sorted")
    public String getAllReportsSortedByDate(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> patientId, Model model) {
        List<Report> reports = reportService.getAllReportsSortedByDate();
        List<ReportResponse> sortedReports = reportMapper.mapListToResponse(reports);
        model.addAttribute("reports",sortedReports);
        return "reports";
    }


}
