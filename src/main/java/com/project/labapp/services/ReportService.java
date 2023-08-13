package com.project.labapp.services;

import com.project.labapp.entities.Patient;
import com.project.labapp.entities.Report;
import com.project.labapp.entities.User;
import com.project.labapp.repos.ReportRepository;
import com.project.labapp.requests.ReportCreateRequest;
import com.project.labapp.requests.ReportUpdateRequest;
import com.project.labapp.responses.ReportResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private ReportRepository reportRepository;

    private UserService userService;

    private PatientService patientService;

    //private final String UPLOAD_DIR = "./images/";

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

    public ReportResponse getOneReportById(Long reportId) {
        Report report = reportRepository.findById(reportId).orElse(null);
        if (report != null) {
            return new ReportResponse(report);
        }
        return null;
    }

    //DOSYA YOLU OLUŞTURMA KAYDETME
    /*public String saveImage(MultipartFile image){
        String uploadDir = "./images";
        String filename = image.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, filename);

        try {
            // Dosyayı kaydet
            Files.copy(image.getInputStream(), filePath);
        } catch (IOException e) {
            // Hata durumunda yapılacak işlemi burada ele alabilirsiniz
            e.printStackTrace();
            return null;
        }
    } */


    //BYTE OLARAK KAYDETME
    /*public String saveImage(MultipartFile image) {
        try {
            // Image'ı projeye kaydetme işlemini gerçekleştirin
            byte[] bytes = image.getBytes();
            String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path path = Paths.get(uploadPath + File.separator + fileName);
            Files.write(path, bytes);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Image kaydetme işlemi başarısız.", e);
        }
    }*/

    public Report createOneReport(ReportCreateRequest newReportRequest) throws IOException {

        User user = userService.getOneUserById(newReportRequest.getUserId());
        if (user == null)
            return null;
        Patient patient = patientService.getOnePatientById(newReportRequest.getPatientId());
        if (patient == null)
            return null;
        System.out.println("newReportRequest.getUserId()" + newReportRequest.getUserId());
        System.out.println("newReportRequest" + newReportRequest);

        byte[] imageBytes = newReportRequest.getReportImageFile().getBytes();

        Report toSave = new Report();
        toSave.setFileNo(newReportRequest.getFileNo());
        toSave.setReportDate(newReportRequest.getReportDate());
        toSave.setReportImage(imageBytes);
        toSave.setDiagnosisMade(newReportRequest.getDiagnosisMade());
        toSave.setDiagnosisDetail(newReportRequest.getDiagnosisDetail());
        toSave.setUser(user);
        toSave.setPatient(patient);
        return reportRepository.save(toSave);
    }

    public Report updateOneReportById(Long reportId, ReportUpdateRequest updateReport) throws IOException {

        byte[] imageBytes = updateReport.getReportImageFile().getBytes();

        Optional<Report> report = reportRepository.findById(reportId);
        if (report.isPresent()){
            Report toUpdate = report.get();
            toUpdate.setDiagnosisMade(updateReport.getDiagnosisMade());
            toUpdate.setDiagnosisDetail(updateReport.getDiagnosisDetail());
            toUpdate.setReportImage(imageBytes);
            reportRepository.save(toUpdate);
            return toUpdate;
        }
        return null;
    }

    public Report deleteOneReportById(Long reportId) {
        reportRepository.deleteById(reportId);
        return null;
    }


    public List<ReportResponse> searchReports(String patientFirstName, String patientLastName, String patientTC, String userFirstName, String userLastName) {
        List<Report> reports;

        if (patientFirstName != null && patientLastName != null && patientTC != null && userFirstName != null && userLastName != null) {
            reports = reportRepository.findByPatient_PatientFirstNameContainingIgnoreCaseAndPatient_PatientLastNameContainingIgnoreCaseAndPatient_PatientTCContainingIgnoreCaseAndUser_UserFirstNameContainingIgnoreCaseAndUser_UserLastNameContainingIgnoreCase(
                    patientFirstName, patientLastName, patientTC, userFirstName, userLastName);
        } else if (patientFirstName != null && patientLastName != null && patientTC != null) {
            reports = reportRepository.findByPatient_PatientFirstNameContainingIgnoreCaseAndPatient_PatientLastNameContainingIgnoreCaseAndPatient_PatientTCContainingIgnoreCase(
                    patientFirstName, patientLastName, patientTC);
        } else if (userFirstName != null && userLastName != null) {
            reports = reportRepository.findByUser_UserFirstNameContainingIgnoreCaseAndUser_UserLastNameContainingIgnoreCase(
                    userFirstName, userLastName);
        } else {
            // Tüm raporları getir (eğer gerekirse başka bir kriter ekleyebilirsiniz)
            reports = reportRepository.findAll();
        }

        List<ReportResponse> reportResponses = new ArrayList<>();

        for (Report report : reports) {
            reportResponses.add(new ReportResponse(report));
        }

        return reportResponses;
    }

    public List<Report> getAllReportsSortedByDate() {
        return reportRepository.findAllByOrderByReportDateDesc();
    }
}
