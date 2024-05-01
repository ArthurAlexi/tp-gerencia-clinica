package com.clinica.tpGerencia.configs;

import com.clinica.tpGerencia.dtos.responses.ExceptionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDTO> threatDuplicateEntry(DataIntegrityViolationException exception) {
        return ResponseEntity.badRequest().body(
                new ExceptionDTO(HttpStatus.BAD_REQUEST, "already registered entity")
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> threat404(EntityNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> threatGeneralException(Exception exception) {
        return ResponseEntity.internalServerError().body(
                new ExceptionDTO( HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage() != null ? exception.getMessage() : "Internal Server Error")
        );
    }

}
