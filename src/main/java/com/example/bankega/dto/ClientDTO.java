package com.example.bankega.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ClientDTO {
    private Long id;
    private String nom;
    private String prenom;
    private LocalDateTime dateNais;
    private String sexe;
    private String adresse;
    private String  tel;
    private String  nationalite;
    private String  courriel;
    private Boolean  actif;

}
