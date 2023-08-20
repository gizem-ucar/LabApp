package com.project.labapp.web;

import com.project.labapp.entities.Patient;
import com.project.labapp.responses.ReportResponse;
import com.project.labapp.services.PatientService;
import com.project.labapp.services.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class PatientWebController {

    private PatientService patientService;
    private ReportService reportService;

    public PatientWebController(PatientService patientService, ReportService reportService){
        this.patientService = patientService;
        this.reportService = reportService;
    }

    @GetMapping("/web/patients")
    public String patients(Model model){
        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute("patients",patients);
        return "patients";
    }

    @GetMapping("/web/patients/{patientId}")
    public String getOnePatient(@PathVariable Long patientId, Model model){
        Patient patient = patientService.getOnePatientById(patientId);
        if (patient != null){
            List<ReportResponse> reports = reportService.getAllReports(Optional.empty(), Optional.of(patientId));
            model.addAttribute("reports", reports);
            model.addAttribute("patient", patient);
            return "patientProfile";
        }else {
            // Handle report not found
            return "errorPage"; // Örnek olarak hata sayfasına yönlendirme
        }
    }

    @GetMapping("/web/patientAdd")
    public String getPatientAdd(Model model) {
        return "patientAdd";
    }

    @GetMapping("/web/patientUpdate/{patientId}")
    public String getPatientAdd(@PathVariable Long patientId, Model model) {
        Patient patient = patientService.getOnePatientById(patientId);
        if (patient != null){
            model.addAttribute("patient", patient);
            return "patientUpdate";
        }else {
            return "errorPage";
        }

    }


    @PostMapping(consumes = "multipart/form-data", path ="/web/patientUpdate/{patientId}")
    public String updateOnePatient(@PathVariable Long patientId, @Valid @ModelAttribute Patient newPatient, Model model){
        Patient patient = patientService.updateOnePatient(patientId, newPatient);
        model.addAttribute("patient",patient);
        return "redirect:/web/patients/{patientId}";
    }

    @PostMapping(consumes = "multipart/form-data", path ="/web/patientAdd")
    public String createPatient(@Valid @ModelAttribute Patient newPatient){
        patientService.saveOnePatient(newPatient);
        return "patients";
    }

    @PostMapping("/web/patientDelete/{patientId}")
    public String deleteOnePatient(@PathVariable Long patientId, Model model){
        patientService.deleteById(patientId);
        return "redirect:/web/patients";
    }

/*    @PostMapping
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
