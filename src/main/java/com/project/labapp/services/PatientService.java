package com.project.labapp.services;

import com.project.labapp.entities.Patient;
import com.project.labapp.repos.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }


    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getOnePatientById(Long patientId) {
        return patientRepository.findById(patientId).orElse(null);
    }

    public Patient saveOnePatient(Patient newPatient) {
        return patientRepository.save(newPatient);
    }

    public void deleteById(Long patientId) {
        patientRepository.deleteById(patientId);
    }

    public Patient updateOnePatient(Long patientId, Patient newPatient) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        if (patient.isPresent()){
            Patient foundPatient = patient.get();
            foundPatient.setPatientTC(newPatient.getPatientTC());
            foundPatient.setPatientFirstName(newPatient.getPatientFirstName());
            foundPatient.setPatientLastName(newPatient.getPatientLastName());
            patientRepository.save(foundPatient);
            return foundPatient;
        }else
            return null;
    }
}
