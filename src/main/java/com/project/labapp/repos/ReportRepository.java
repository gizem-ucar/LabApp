package com.project.labapp.repos;

import com.project.labapp.entities.Report;
import com.project.labapp.responses.ReportResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByPatientPatientId(Long patientId);

    List<Report> findByUserId(Long userId);

    List<Report> findByUserIdAndPatientPatientId(Long userId, Long patientId);


    List<Report> findByPatient_PatientFirstNameContainingIgnoreCaseAndPatient_PatientLastNameContainingIgnoreCaseAndPatient_PatientTCContainingIgnoreCase(
            String patientFirstName, String patientLastName, String patientTC);

    List<Report> findByUser_UserFirstNameContainingIgnoreCaseAndUser_UserLastNameContainingIgnoreCase(
            String userFirstName, String userLastName);

    List<Report> findByPatient_PatientFirstNameContainingIgnoreCaseAndPatient_PatientLastNameContainingIgnoreCaseAndPatient_PatientTCContainingIgnoreCaseAndUser_UserFirstNameContainingIgnoreCaseAndUser_UserLastNameContainingIgnoreCase(
            String patientFirstName, String patientLastName, String patientTC,
            String userFirstName, String userLastName);

    List<Report> findAllByOrderByReportDateDesc();
}
