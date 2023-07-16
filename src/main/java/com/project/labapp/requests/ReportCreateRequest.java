package com.project.labapp.requests;

import lombok.Data;

import java.util.Date;

@Data
public class ReportCreateRequest {

    Long reportId;
    String fileNo;
    String diagnosisMade;
    String diagnosisDetail;
    Date reportDate;
    String reportImage;
    Long userId;
    Long patientId;

}
