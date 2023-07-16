package com.project.labapp.services;

import com.project.labapp.entities.Report;
import com.project.labapp.entities.User;
import com.project.labapp.repos.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository){
        this.reportRepository = reportRepository;
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public List<Report> getAllReports(Optional<Long> patientId){
        if (patientId.isPresent())
            return reportRepository.findByPatientPatientId(patientId.get());
        return reportRepository.findAll();
    }

    //public List<Report> getAllReportsUserId(Optional<Long> userId){
    //    if (userId.isPresent())
    //        return reportRepository.findByUserId(userId.get());
    //    return reportRepository.findAll();
    //}

    public Report getOneReportById(Long reportId) {
        return reportRepository.findById(reportId).orElse(null);
    }

    public Report createOneReport(Report newReport) {
        return reportRepository.save(newReport);
    }
}
