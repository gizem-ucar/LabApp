package com.project.labapp.controllers;

import com.project.labapp.entities.Patient;
import com.project.labapp.entities.User;
import com.project.labapp.services.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }

    @GetMapping
    public List<Patient> getAllPatients(){
        return patientService.getAllPatients();
    }

    @GetMapping("/{patientId}")
    public Patient getOnePatient(@PathVariable Long patientId){
        //custom exception
        return patientService.getOnePatientById(patientId);
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient newPatient){
        return patientService.saveOnePatient(newPatient);
    }

    @DeleteMapping("/{patientId}")
    public void deleteOnePatient(@PathVariable Long patientId) {
        patientService.deleteById(patientId);
    }

    @PutMapping("/{patientId}")
    public Patient updateOnePatient(@PathVariable Long patientId, @RequestBody Patient newPatient){
        return patientService.updateOnePatient(patientId, newPatient);
    }

}
