package com.project.labapp.responses;

import com.project.labapp.entities.Report;
import lombok.Data;

import java.util.Date;

@Data
public class ReportResponse {
    Long reportId;
    String fileNo;
    String diagnosisMade;
    String diagnosisDetail;
    Date reportDate;
    byte[] reportImage;
    Long userId;
    Long patientId;
    String patientTC;
    String patientFirstName;
    String patientLastName;

    public ReportResponse(Report entity){
        this.reportId = entity.getReportId();
        this.fileNo = entity.getFileNo();
        this.diagnosisMade = entity.getDiagnosisMade();
        this.diagnosisDetail = entity.getDiagnosisDetail();
        this.reportDate = entity.getReportDate();
        this.reportImage = entity.getReportImage();
        this.userId = entity.getUser().getId();
        this.patientId = entity.getPatient().getPatientId();
        this.patientTC = entity.getPatient().getPatientTC();
        this.patientFirstName = entity.getPatient().getPatientFirstName();
        this.patientLastName = entity.getPatient().getPatientLastName();
    }
}
