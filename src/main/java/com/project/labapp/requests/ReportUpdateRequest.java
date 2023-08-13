package com.project.labapp.requests;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ReportUpdateRequest {

    String diagnosisMade;

    String diagnosisDetail;

    MultipartFile reportImageFile;

}
