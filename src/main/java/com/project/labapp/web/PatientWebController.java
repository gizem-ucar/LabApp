package com.project.labapp.web;

import com.project.labapp.entities.Patient;
import com.project.labapp.services.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PatientWebController {

    private PatientService patientService;

    public PatientWebController(PatientService patientService){
        this.patientService = patientService;
    }

    @GetMapping("/web/patients")
    public String patients(Model model){
        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute("patients",patients);
        return "patients";
    }

/*    @GetMapping("/web/patients/{patientId}")
    public Patient getOnePatient(@PathVariable Long patientId){
        //custom exception
        return patientService.getOnePatientById(patientId);
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient newPatient){
        return patientService.saveOnePatient(newPatient);
    }

    @DeleteMapping("/web/patients/{patientId}")
    public void deleteOnePatient(@PathVariable Long patientId) {
        patientService.deleteById(patientId);
    }

    @PutMapping("/web/patients/{patientId}")
    public Patient updateOnePatient(@PathVariable Long patientId, @RequestBody Patient newPatient){
        return patientService.updateOnePatient(patientId, newPatient);
    }*/

}
