package com.example.bankega.dto;


import lombok.Data;

@Data
public class VirementRequest {
    private  String numCompteSource;
    private  String numCompteDest;
    private  double montant;

}
