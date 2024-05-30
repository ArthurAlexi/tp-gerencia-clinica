package com.clinica.tpGerencia.controllers;

import com.clinica.tpGerencia.dtos.requests.CreatePatientDTO;
import com.clinica.tpGerencia.models.Patient;
import com.clinica.tpGerencia.services.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@CrossOrigin(originPatterns = {"*"})
public class PatientController {

    private final PatientService PATIENT_SERVICE;

    public PatientController(PatientService patientService){
        this.PATIENT_SERVICE = patientService;
    }

    @PostMapping
    public ResponseEntity<?> createPatient(@RequestBody CreatePatientDTO dto){
        if(dto == null) return new  ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.PATIENT_SERVICE.createPatient(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable Integer id){
        return ResponseEntity.ok(this.PATIENT_SERVICE.getPatientById(id));
    }

    @GetMapping()
    public ResponseEntity<?> getAllPatients(){
        return ResponseEntity.ok(this.PATIENT_SERVICE.getAllPatients());
    }

    @PutMapping()
    public ResponseEntity<?> updatePatient(@RequestBody Patient patient) {
        if(patient == null) return new  ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(202).body(this.PATIENT_SERVICE.updatePatient(patient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable Integer id){
        return ResponseEntity.ok(this.PATIENT_SERVICE.deletePatient(id));
    }

}
