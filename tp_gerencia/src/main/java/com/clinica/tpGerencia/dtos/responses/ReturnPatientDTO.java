package com.clinica.tpGerencia.dtos.responses;

import java.time.LocalDate;

public record ReturnPatientDTO(

        Integer id,
        String firstName,
        String lastName,
        Character sex,
        String cpf,
        LocalDate birthDate,
        Double height,
        Double weight,
        Double imc,
        String imcStatus,
        Double idealWeight
) {
}
