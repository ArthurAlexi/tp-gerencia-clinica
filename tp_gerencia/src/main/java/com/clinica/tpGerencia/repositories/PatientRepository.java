package com.clinica.tpGerencia.repositories;

import com.clinica.tpGerencia.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Override
    Optional<Patient> findById(Integer integer);
}
