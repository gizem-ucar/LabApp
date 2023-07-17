package com.project.labapp.services;

import com.project.labapp.entities.Patient;
import com.project.labapp.entities.Report;
import com.project.labapp.entities.User;
import com.project.labapp.repos.ReportRepository;
import com.project.labapp.requests.ReportCreateRequest;
import com.project.labapp.requests.ReportUpdateRequest;
import com.project.labapp.responses.ReportResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    //public List<Report> getAllReportsByPatientId(Optional<Long> patientId){
    //    if (patientId.isPresent())
    //        return reportRepository.findByPatientPatientId(patientId.get());
    //    return reportRepository.findAll();
    //}

    public List<ReportResponse> getAllReports(Optional<Long> userId, Optional<Long> patientId){
        List<Report> list;
        if (userId.isPresent() && patientId.isPresent()){
            list = reportRepository.findByUserIdAndPatientPatientId(userId.get(), patientId.get());
        }else if (userId.isPresent()){
            list = reportRepository.findByUserId(userId.get());
        }else if(patientId.isPresent()){
            list = reportRepository.findByPatientPatientId(patientId.get());
        }else
            list = reportRepository.findAll();
        return list.stream().map(r -> new ReportResponse(r)).collect(Collectors.toList());
    }

    public Report getOneReportById(Long reportId) {
        return reportRepository.findById(reportId).orElse(null);
    }

    public Report createOneReport(ReportCreateRequest newReportRequest) {

        User user = userService.getOneUserById(newReportRequest.getUserId());
        if (user == null)
            return null;
        Patient patient = patientService.getOnePatientById(newReportRequest.getPatientId());
        if (patient == null)
            return null;
        //if (user != null && patient != null){
            //
        //}
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

    public Report updateOneReportById(Long reportId, ReportUpdateRequest updateReport) {
        Optional<Report> report = reportRepository.findById(reportId);
        if (report.isPresent()){
            Report toUpdate = report.get();
            toUpdate.setDiagnosisMade(updateReport.getDiagnosisMade());
            toUpdate.setDiagnosisDetail(updateReport.getDiagnosisDetail());
            toUpdate.setReportImage(updateReport.getReportImage());
            reportRepository.save(toUpdate);
            return toUpdate;
        }
        return null;
    }

    public Report deleteOneReportById(Long reportId) {
        reportRepository.deleteById(reportId);
        return null;
    }
}
