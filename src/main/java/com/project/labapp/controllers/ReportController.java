package com.project.labapp.controllers;

import com.project.labapp.entities.Report;
import com.project.labapp.requests.ReportCreateRequest;
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

    //@GetMapping
    //public List<Report> getAllReports(){
    //    return reportService.getAllReports();
    //}

    @GetMapping
    public List<Report> getAllReports(@RequestParam Optional<Long> userId){
        return reportService.getAllReports(userId);
    }

    //@GetMapping("/patient/{patientId}")
    //public List<Report> getAllReportsByPatientId(@PathVariable Optional<Long> patientId){
    //    return reportService.getAllReportsByPatientId(patientId);
    //}

    @GetMapping("/patient/{patientId}")
    public List<Report> getAllReportsByPatientId(@PathVariable Optional<Long> patientId){
        return reportService.getAllReportsByPatientId(patientId);
    }

    @PostMapping
    public Report createOneReport(@RequestBody ReportCreateRequest newReportRequest){
        return reportService.createOneReport(newReportRequest);
    }

    @GetMapping("/{reportId}")
    public Report getOneReport(@PathVariable Long reportId){
        return reportService.getOneReportById(reportId);
    }

}
