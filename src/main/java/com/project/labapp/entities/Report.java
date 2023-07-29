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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long reportId;

    String fileNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    @JsonIgnore   //ignore etmek
    User user;


    String diagnosisMade;

    String diagnosisDetail;

    @Temporal(TemporalType.TIMESTAMP)
    Date reportDate;

    //String reportImage;

    @Column(name = "report_image", columnDefinition = "bytea")
    byte[] reportImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="patient_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore   //ignore etmek
    Patient patient;

}
