package com.project.labapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore   //ignore etmek
    User user;

    String diagnosisMade;

    String diagnosisDetail;


    Date reportDate;

    String reportImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="patient_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore   //ignore etmek
    Patient patient;

    public Long getPatientId() {
        return patient != null ? patient.getPatientId() : null;
    }

    public void setPatientId(Long patientId) {
        if (patient == null) {
            patient = new Patient();
        }
        patient.setPatientId(patientId);
    }
}
