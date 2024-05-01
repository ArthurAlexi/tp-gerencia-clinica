package com.clinica.tpGerencia.services;

import com.clinica.tpGerencia.dtos.requests.CreatePatientDTO;
import com.clinica.tpGerencia.dtos.responses.ReturnPatientDTO;
import com.clinica.tpGerencia.models.Patient;
import com.clinica.tpGerencia.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
public class PatientService {

    private final PatientRepository PATIENT_REPOSITORY;
    private final Map<Double, String> imcStatus = new HashMap<>() {{
        put(17.0, "Very underweight");
        put(18.49, "Under weight");
        put(24.99, "Normal weight");
        put(29.99, "Overweight");
        put(34.99, "Obesity I");
        put(39.99, "Obesity II");
        put(40.0, "Obesity III");

    }};

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
                    dto.weight(),
                    dto.cpf(),
                    this.calculateImc(dto.height(), dto.weight())
                );

        return this.PATIENT_REPOSITORY.save(newPatient);
    }

    public ReturnPatientDTO getPatientById(Integer id) {
        Patient patient = this.PATIENT_REPOSITORY.findById(id)
                .orElseThrow(() -> new RuntimeException("User does not exist"));
        return new ReturnPatientDTO(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getSex(),
                patient.getCpf(),
                patient.getBirthDate(),
                patient.getHeight().doubleValue(),
                patient.getWeight(),
                patient.getImc(),
                getImcStatus(patient.getImc()),
                calculateIdealWeight(patient)
        );
    }



    public  Integer updatePatient(Patient patient) {
        patient.setImc(calculateImc(patient.getHeight().doubleValue(), patient.getWeight()));
        patient.setAge(calculateAge(patient.getBirthDate()));
        this.PATIENT_REPOSITORY.saveAndFlush(patient);
        return patient.getId();
    }

    public Integer deletePatient(Integer id) {
        Patient patient = this.PATIENT_REPOSITORY.findById(id)
                .orElseThrow(() -> new RuntimeException("User does not exist"));

        this.PATIENT_REPOSITORY.delete(patient);

        return patient.getId();
    }

    public List<ReturnPatientDTO> getAllPatients() {
        List<Patient> patients = this.PATIENT_REPOSITORY.findAll();
        List<ReturnPatientDTO> returnPatientDTOS = new ArrayList<>();

        for (Patient patient: patients) {
            ReturnPatientDTO dto = new ReturnPatientDTO(
                    patient.getId(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getSex(),
                    patient.getCpf(),
                    patient.getBirthDate(),
                    patient.getHeight().doubleValue(),
                    patient.getWeight(),
                    patient.getImc(),
                    getImcStatus(patient.getImc()),
                    calculateIdealWeight(patient)
            );
            returnPatientDTOS.add(dto);
        }
        return returnPatientDTOS;
    }

    private boolean validateCpf(String cpf) {
        cpf = cpf.replace(".", "");
        cpf = cpf.replace("-", "");

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

    private String getImcStatus(Double imc) {
        for (Map.Entry<Double, String> entry : imcStatus.entrySet()) {
            if (imc < entry.getKey()) {
                return entry.getValue();
            }
        }
        return "Not classified";
    }

    private Double calculateIdealWeight(Patient patient){
        if(patient.getSex().toString().equalsIgnoreCase("m")){
            return 72.7 * patient.getHeight() - 58;
        }

        return 62.1 * patient.getHeight() - 44.7;
    }

}
