package com.project.labapp.requests;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class ReportCreateRequest {

    //Long reportId;
    String fileNo;
    String diagnosisMade;
    String diagnosisDetail;
    Date reportDate;
    MultipartFile reportImageFile;
    Long userId;
    Long patientId;

}
