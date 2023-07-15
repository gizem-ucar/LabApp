package com.project.labapp.repos;

import com.project.labapp.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByPatientPatientId(Long patientId);

    List<Report> findByUserId(Long userId);
}
