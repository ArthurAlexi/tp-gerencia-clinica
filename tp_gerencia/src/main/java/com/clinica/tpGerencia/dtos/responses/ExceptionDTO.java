package com.clinica.tpGerencia.dtos.responses;

import org.springframework.http.HttpStatus;

public record ExceptionDTO(
        HttpStatus statusCode,
        String message
) {
}
