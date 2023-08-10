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

    //@GetMapping("/patient/{patientId}")
    //public List<Report> getAllReportsByPatientId(@PathVariable Optional<Long> patientId){
    //    return reportService.getAllReportsByPatientId(patientId);
    //}

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

    //BYTE İLE VERİ TABANINA KAYDETME
    /*@PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<String> addReport(@RequestParam("file") MultipartFile reportImageFile, @ModelAttribute ReportCreateRequest newReportRequest) {
        // Image'ı projeye kaydetmek için servis metodu çağırın
        String imagePath = reportService.saveImage(reportImageFile);

        // Veritabanına image ile ilgili bilgileri kaydedin
        newReportRequest.setImagePath(imagePath);
        reportService.saveReport(newReportRequest);

        return ResponseEntity.ok("Rapor başarıyla eklendi.");
    }*/

    /*@PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Report> createOneReport(@ModelAttribute ReportCreateRequest newReportRequest){

        Date reportDate = newReportRequest.getReportDate();
        newReportRequest.setReportDate(reportDate);

        MultipartFile imageFile = newReportRequest.getReportImageFile();
        newReportRequest.setReportImageFile(imageFile);

        //String reportName = reportService.saveImage(newReportRequest.getReportImageFile());
        Report createdReport = reportService.createOneReport(newReportRequest);

        if (createdReport != null)
        {
            return ResponseEntity.ok(createdReport);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

    /*@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Report> createOneReport(@RequestParam("fileNo") String fileNo,
                                                  @RequestParam("userId") Long userId,
                                                  @RequestParam("patientId") Long patientId,
                                                  @RequestParam("diagnosisMade") String diagnosisMade,
                                                  @RequestParam("diagnosisDetail") String diagnosisDetail,
                                                  @RequestParam("reportDate") Date reportDate,
                                                  @RequestParam("reportImageFile") MultipartFile reportImageFile) {

        ReportCreateRequest newReportRequest = new ReportCreateRequest();
        newReportRequest.setFileNo(fileNo);
        newReportRequest.setUserId(userId);
        newReportRequest.setDiagnosisMade(diagnosisMade);
        newReportRequest.setDiagnosisDetail(diagnosisDetail);
        newReportRequest.setReportDate(reportDate);
        newReportRequest.setReportImageFile(reportImageFile);

        Report createdReport = reportService.createOneReport(newReportRequest, reportImageFile);

        if (createdReport != null) {
            return ResponseEntity.ok(createdReport);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    } */

    @GetMapping("/{reportId}")
    public ReportResponse getOneReport(@PathVariable Long reportId){
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
