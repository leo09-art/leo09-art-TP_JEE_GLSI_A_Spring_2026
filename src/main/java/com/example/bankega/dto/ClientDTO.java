package com.example.bankega.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientDTO {
    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNais;
    private String sexe;
    private String adresse;
    private String  tel;
    private String  nationalite;

}
