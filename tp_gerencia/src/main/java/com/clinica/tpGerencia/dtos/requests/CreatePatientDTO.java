package com.clinica.tpGerencia.dtos.requests;

import java.time.LocalDate;

public record CreatePatientDTO(
       String firstName,
       String lastName,
       Character sex,
       String cpf,
       LocalDate birthDate,
       Double height,
       Double weight

) {
}
