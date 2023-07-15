package com.project.labapp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="reports")
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    @Id
    Long reportId;

    String fileNo;

    Long userId;

    String diagnosisMade;

    String diagnosisDetail;


    Date reportDate;

    String reportImage;

    Long patientId;

}
