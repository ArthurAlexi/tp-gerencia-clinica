package com.clinica.tpGerencia.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    private Character sex;

    private LocalDate birthDate;

    private Byte age;

    private Short height;

    private Double Weight;

    @Column(unique = true)
    private String cpf;

    private Double imc;

    public Patient(
            String firstName,
            String lastName,
            Character sex,
            LocalDate birthDate,
            Byte age,
            Short height,
            Double weight,
            String cpf,
            Double imc
    ){
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.birthDate = birthDate;
        this.age = age;
        this.height = height;
        this.Weight = weight;
        this.cpf = cpf;
        this.imc = imc;
    }

}
