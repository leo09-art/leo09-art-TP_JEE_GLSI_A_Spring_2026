package com.example.bankega.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
public class InscriptionRequest {
    @Column(nullable = false)
    private String  nom;

    @Column(nullable = false)
    private String  prenom;

    @Column(nullable = false)
    private LocalDateTime dateNais;

    private String  sexe;

    private String  adresse;

    @Column(nullable = false)
    private String  tel;

    @Column(nullable = false)
    private String  courriel;

    private String  nationalite;

    @Column(nullable = false)
    private  String password;
}
