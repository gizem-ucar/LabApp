package com.project.labapp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name="patients")
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    @Id
    Long patientId;

    String patientTC;

    String patientFirstName;

    String patientLastName;


}
