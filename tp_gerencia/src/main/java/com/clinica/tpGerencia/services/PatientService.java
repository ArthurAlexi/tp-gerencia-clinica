package com.clinica.tpGerencia.services;

import com.clinica.tpGerencia.dtos.requests.CreatePatientDTO;
import com.clinica.tpGerencia.models.Patient;
import com.clinica.tpGerencia.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class PatientService {

    private final PatientRepository PATIENT_REPOSITORY;

    public PatientService(PatientRepository patientRepository){
        this.PATIENT_REPOSITORY = patientRepository;
    }

    public Patient createPatient(CreatePatientDTO dto) {

        if(!validateCpf(dto.cpf())){
            throw new RuntimeException("CPF is not valid");
        }

        Patient newPatient = new Patient(
                    dto.firstName(),
                    dto.lastName(),
                    dto.sex(),
                    dto.birthDate(),
                    this.calculateAge(dto.birthDate()),
                    dto.height().shortValue(),
                    dto.Weight(),
                    dto.cpf(),
                    this.calculateImc(dto.height(), dto.Weight())
                );

        return this.PATIENT_REPOSITORY.save(newPatient);
    }

    public Patient getPatientById(Integer id) {
        return  this.PATIENT_REPOSITORY.findById(id)
                .orElseThrow(() -> new RuntimeException("User does not exist"));
    }

    public  Integer updatePatient(Patient patient) {
        this.PATIENT_REPOSITORY.saveAndFlush(patient);
        return patient.getId();
    }

    public Integer deltePatient(Integer id) {
        Patient patient = this.PATIENT_REPOSITORY.findById(id)
                .orElseThrow(() -> new RuntimeException("User does not exist"));

        this.PATIENT_REPOSITORY.delete(patient);

        return patient.getId();
    }

    public List<Patient> getAllPatients() {
        return this.PATIENT_REPOSITORY.findAll();
    }

    private boolean validateCpf(String cpf) {
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d{11}")) {
            return false;
        }

        int[] cpfDigits = new int[11];
        for (int i = 0; i < 11; i++) {
            cpfDigits[i] = Integer.parseInt(String.valueOf(cpf.charAt(i)));
        }

        int v1 = 0;
        int v2 = 0;
        for (int i = 0; i < 9; i++) {
            v1 += cpfDigits[i] * (9 - (i % 10));
            v2 += cpfDigits[i] * (9 - ((i + 1) % 10));
        }

        v1 = (v1 % 11) % 10;
        v2 += v1 * 9;
        v2 = (v2 % 11) % 10;

        int calculatedV1 = cpfDigits[9];
        int calculatedV2 = cpfDigits[10];

        return v1 == calculatedV1 && v2 == calculatedV2;
    }
    private Byte calculateAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthDate, currentDate);
        return (byte) period.getYears();
    }

    private Double calculateImc(Double height, Double weight) {
        if (height <= 0 || weight <= 0) {
            throw new IllegalArgumentException("Height and weight must be greater than zero.");
        }

        return weight / (height * height);
    }
}
