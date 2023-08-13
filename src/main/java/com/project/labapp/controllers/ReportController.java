package com.project.labapp.controllers;

import com.project.labapp.config.ReportMapper;
import com.project.labapp.entities.Report;
import com.project.labapp.requests.ReportCreateRequest;
import com.project.labapp.requests.ReportUpdateRequest;
import com.project.labapp.responses.ReportResponse;
import com.project.labapp.services.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private ReportService reportService;

    private ReportMapper reportMapper;

    public ReportController(ReportService reportService, ReportMapper reportMapper){
        this.reportService = reportService;
        this.reportMapper = reportMapper;
    }

    @GetMapping
    public List<ReportResponse> getAllReports(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> patientId){
        return reportService.getAllReports(userId, patientId);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Report> createOneReport (
            @RequestParam("file") MultipartFile reportImageFile,
            @ModelAttribute ReportCreateRequest newReportRequest
    ) throws IOException {
        newReportRequest.setReportImageFile(reportImageFile);

        Date reportDate = newReportRequest.getReportDate();
        newReportRequest.setReportDate(reportDate);

        Report createdReport = reportService.createOneReport(newReportRequest);

        if (createdReport != null) {
            return ResponseEntity.ok(createdReport);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/{reportId}")
    public ReportResponse getOneReport(@PathVariable Long reportId){
        return reportService.getOneReportById(reportId);
    }

    @PutMapping("/{reportId}")
    public Report updateOneReport(@PathVariable Long reportId,@RequestParam("file") MultipartFile reportImageFile, @ModelAttribute ReportUpdateRequest updateReport)throws IOException{
        updateReport.setReportImageFile(reportImageFile);
        return reportService.updateOneReportById(reportId, updateReport);
    }


    @DeleteMapping("/{reportId}")
    public Report deleteOneReport(@PathVariable Long reportId){
        return reportService.deleteOneReportById(reportId);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ReportResponse>> searchReports(@RequestParam(required = false) String patientFirstName,
                                                              @RequestParam(required = false) String patientLastName,
                                                              @RequestParam(required = false) String patientTC,
                                                              @RequestParam(required = false) String userFirstName,
                                                              @RequestParam(required = false) String userLastName) {
        List<ReportResponse> reportResponses = reportService.searchReports(patientFirstName, patientLastName, patientTC, userFirstName, userLastName);
        return ResponseEntity.ok(reportResponses);
    }

    @GetMapping("/sorted")
    public List<ReportResponse> getAllReportsSortedByDate(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> patientId) {
        List<Report> sortedReports = reportService.getAllReportsSortedByDate();
        return reportMapper.mapListToResponse(sortedReports);
    }
}
