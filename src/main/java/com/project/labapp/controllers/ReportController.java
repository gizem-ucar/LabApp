package com.project.labapp.controllers;

import com.project.labapp.entities.Report;
import com.project.labapp.services.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private ReportService reportService;

    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    @GetMapping
    public List<Report> getAllReportsUserId(@RequestParam Optional<Long> userId){
        return reportService.getAllReportsUserId(userId);
    }
    @GetMapping("/{patientId}")
    public List<Report> getAllReports(@PathVariable Optional<Long> patientId){
        return reportService.getAllReports(patientId);
    }

    @PostMapping
    public Report createOneReport(@RequestBody Report newReport){
        return reportService.createOneReport(newReport);
    }

    @GetMapping("/{reportId}")
    public Report getOneReport(@PathVariable Long reportId){
        return reportService.getOneReportById(reportId);
    }

}
