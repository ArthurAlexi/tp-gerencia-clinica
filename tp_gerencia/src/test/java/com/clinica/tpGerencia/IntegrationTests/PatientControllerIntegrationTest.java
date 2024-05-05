package com.clinica.tpGerencia.IntegrationTests;

import com.clinica.tpGerencia.controllers.PatientController;
import com.clinica.tpGerencia.dtos.requests.CreatePatientDTO;
import com.clinica.tpGerencia.dtos.responses.ReturnPatientDTO;
import com.clinica.tpGerencia.models.Patient;
import com.clinica.tpGerencia.services.PatientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PatientControllerIntegrationTest {
    @MockBean
    private PatientService patientService;

    @Test
    public void testCreatePatient_Success() {
        PatientController patientController = new PatientController(patientService);

        CreatePatientDTO dto = new CreatePatientDTO("John", "Doe", 'm',"12345678901", LocalDate.now(), 180.0, 80.0 );
        Patient createdPatient = new Patient("John", "Doe", 'm', LocalDate.now(), (byte) 30, (short) 180, 80.0, "12345678901", 24.69);

        Mockito.when(patientService.createPatient(dto)).thenReturn(createdPatient);

        ResponseEntity<?> responseEntity = patientController.createPatient(dto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdPatient, responseEntity.getBody());
    }

    @Test
    public void testGetPatientById_Success() {
        PatientController patientController = new PatientController(patientService);

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

        Mockito.when(patientService.getPatientById(1)).thenReturn(patient);

        ResponseEntity<?> responseEntity = patientController.getPatientById(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(patient, responseEntity.getBody());
    }

    @Test
    public void testUpdatePatient_Success() {
        PatientController patientController = new PatientController(patientService);

        Patient patientToUpdate = new Patient("John", "Doe", 'm', LocalDate.now(), (byte) 30, (short) 180, 80.0, "12345678901", 24.69);

        Mockito.when(patientService.updatePatient(patientToUpdate)).thenReturn(1);

        ResponseEntity<?> responseEntity = patientController.updatePatient(patientToUpdate);

        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody());
    }

    @Test
    public void testDeletePatient_Success() {
        PatientController patientController = new PatientController(patientService);

        Mockito.when(patientService.deletePatient(1)).thenReturn(1);

        ResponseEntity<?> responseEntity = patientController.deletePatient(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody());
    }

    @Test
    public void testGetAllPatients_Success() {
        PatientController patientController = new PatientController(patientService);

        List<ReturnPatientDTO> patients = new ArrayList<>();
        patients.add(new ReturnPatientDTO(1,
                "test",
                "test",
                'm',
                "23797659016",
                LocalDate.of(1990, 1, 1),
                170.0,
                70.0,
                12.0,
                "",
                23.5));

        patients.add(new ReturnPatientDTO(1,
                "test",
                "test",
                'f',
                "12345678901",
                LocalDate.of(1990, 1, 1),
                170.0,
                70.0,
                12.0,
                "",
                23.5));

        Mockito.when(patientService.getAllPatients()).thenReturn(patients);

        ResponseEntity<?> responseEntity = patientController.getAllPatients();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(patients, responseEntity.getBody());
    }
}
