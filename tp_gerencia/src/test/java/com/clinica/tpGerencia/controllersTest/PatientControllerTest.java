package com.clinica.tpGerencia.controllersTest;

import com.clinica.tpGerencia.controllers.PatientController;
import com.clinica.tpGerencia.dtos.requests.CreatePatientDTO;
import com.clinica.tpGerencia.dtos.responses.ReturnPatientDTO;
import com.clinica.tpGerencia.models.Patient;
import com.clinica.tpGerencia.services.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreatePatient() {

        CreatePatientDTO createPatientDTO = new CreatePatientDTO(
                "test",
                "test",
                'm',
                "12345678900",
                LocalDate.of(1990, 1, 1),
                170.0,
                70.0
        );

        Patient patient = new Patient(
                1,
                "test",
                "test",
                'm',
                LocalDate.of(1990, 1, 1),
                (byte) 34,
                (short) 170,
                70.0,
                "12345678901",
                23.5
        );
//        when(patientService.createPatient(createPatientDTO)).thenReturn(patient);

        ResponseEntity<?> responseEntity = patientController.createPatient(createPatientDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }

    @Test
    void testGetPatientById() {
        Integer id = 1;
        ReturnPatientDTO patient = new ReturnPatientDTO(
                1,
                "test",
                "test",
                'm',
                "12345678901",
                LocalDate.of(1990, 1, 1),
                170.0,
                70.0,
                12.0,
                "",
                23.5
        );

//        when(patientService.getPatientById(id)).thenReturn(patient);

        ResponseEntity<?> responseEntity = patientController.getPatientById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetAllPatients() {
        List<ReturnPatientDTO> patients = new ArrayList<>();
        // Populate patients list with sample data

//        when(patientService.getAllPatients()).thenReturn(patients);

        ResponseEntity<?> responseEntity = patientController.getAllPatients();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testUpdatePatient() {
        Patient patient = new Patient();
//        when(patientService.updatePatient(patient)).thenReturn(patient.getId());

        ResponseEntity<?> responseEntity = patientController.updatePatient(patient);

        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());

    }

    @Test
    void testDeletePatient() {
        Integer id = 1;
//        when(patientService.deletePatient(id)).thenReturn(id);

        ResponseEntity<?> responseEntity = patientController.deletePatient(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

}
