package com.project.labapp.controllers;

import com.project.labapp.entities.Report;
import com.project.labapp.requests.ReportCreateRequest;
import com.project.labapp.requests.ReportUpdateRequest;
import com.project.labapp.responses.ReportResponse;
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
    public List<ReportResponse> getAllReports(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> patientId){
        return reportService.getAllReports(userId, patientId);
    }

    //@GetMapping("/patient/{patientId}")
    //public List<Report> getAllReportsByPatientId(@PathVariable Optional<Long> patientId){
    //    return reportService.getAllReportsByPatientId(patientId);
    //}

    @PostMapping
    public Report createOneReport(@RequestBody ReportCreateRequest newReportRequest){
        return reportService.createOneReport(newReportRequest);
    }

    @GetMapping("/{reportId}")
    public Report getOneReport(@PathVariable Long reportId){
        return reportService.getOneReportById(reportId);
    }

    @PutMapping("/{reportId}")
    public Report updateOneReport(@PathVariable Long reportId, @RequestBody ReportUpdateRequest updateReport){
        return reportService.updateOneReportById(reportId, updateReport);
    }

    @DeleteMapping("/{reportId}")
    public Report deleteOneReport(@PathVariable Long reportId){
        return reportService.deleteOneReportById(reportId);
    }
}
