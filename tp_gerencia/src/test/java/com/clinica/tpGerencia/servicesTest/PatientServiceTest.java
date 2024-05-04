package com.clinica.tpGerencia.servicesTest;

import com.clinica.tpGerencia.dtos.requests.CreatePatientDTO;
import com.clinica.tpGerencia.dtos.responses.ReturnPatientDTO;
import com.clinica.tpGerencia.models.Patient;
import com.clinica.tpGerencia.repositories.PatientRepository;
import com.clinica.tpGerencia.services.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        patientService = new PatientService(patientRepository);
    }

    @Test
    void testCreatePatient() {
        Byte age = 30;

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
        when(patientRepository.save(any())).thenReturn(patient);

        Patient createdPatient = patientService.createPatient(createPatientDTO);

        assertNotNull(createdPatient);
    }
}
