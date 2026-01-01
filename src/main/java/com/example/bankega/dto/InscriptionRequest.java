package com.example.bankega.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private LocalDate dateNais;

    private String  sexe;

    private String  adresse;

    @Column(nullable = false)
    private String  tel;

    @Column(nullable = false)
    private String  courriel;

    private String  nationalite;

    private  String username;

    @Column(nullable = false)
    private  String password;
}
