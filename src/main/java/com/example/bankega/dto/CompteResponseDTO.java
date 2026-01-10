package com.example.bankega.dto;

import com.example.bankega.entity.Client;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompteResponseDTO {

    private Long id;
    private String num;
    private double solde;
    private String type;
    private LocalDateTime dateCreation;
//    private Client client;
    private Boolean actif;
}
