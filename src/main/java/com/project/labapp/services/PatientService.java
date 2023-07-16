package com.project.labapp.services;

import com.project.labapp.entities.Patient;
import com.project.labapp.repos.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }


    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getOnePatient(Long patientId) {
        return patientRepository.findById(patientId).orElse(null);
    }
}
