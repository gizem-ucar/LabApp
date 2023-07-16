package com.project.labapp.services;

import com.project.labapp.entities.Patient;
import com.project.labapp.entities.Report;
import com.project.labapp.entities.User;
import com.project.labapp.repos.ReportRepository;
import com.project.labapp.requests.ReportCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private ReportRepository reportRepository;

    private UserService userService;

    private PatientService patientService;

    public ReportService(ReportRepository reportRepository, UserService userService, PatientService patientService){

        this.reportRepository = reportRepository;
        this.userService = userService;
        this.patientService = patientService;
    }


    public List<Report> getAllReportsByPatientId(Optional<Long> patientId){
        if (patientId.isPresent())
            return reportRepository.findByPatientPatientId(patientId.get());
        return reportRepository.findAll();
    }

    public List<Report> getAllReports(Optional<Long> userId){
        if (userId.isPresent())
            return reportRepository.findByUserId(userId.get());
        return reportRepository.findAll();
    }

    public Report getOneReportById(Long reportId) {
        return reportRepository.findById(reportId).orElse(null);
    }

    public Report createOneReport(ReportCreateRequest newReportRequest) {

        User user = userService.getOneUser(newReportRequest.getUserId());
        if (user == null)
            return null;
        Patient patient = patientService.getOnePatient(newReportRequest.getPatientId());
        if (patient == null)
            return null;
        Report toSave = new Report();
        toSave.setReportId(newReportRequest.getReportId());
        toSave.setFileNo(newReportRequest.getFileNo());
        toSave.setReportDate(newReportRequest.getReportDate());
        toSave.setReportImage(newReportRequest.getReportImage());
        toSave.setDiagnosisMade(newReportRequest.getDiagnosisMade());
        toSave.setDiagnosisDetail(newReportRequest.getDiagnosisDetail());
        toSave.setUser(user);
        toSave.setPatient(patient);
        return reportRepository.save(toSave);
    }
}
