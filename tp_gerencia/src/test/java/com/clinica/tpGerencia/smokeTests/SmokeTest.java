package com.clinica.tpGerencia.smokeTests;

import com.clinica.tpGerencia.controllers.PatientController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private PatientController patientController;

    @Test
    public void contextLoads() {
        assertNotNull(patientController);
    }
}

