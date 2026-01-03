package com.example.bankega.mapper;

import com.example.bankega.dto.CompteResponseDTO;
import com.example.bankega.entity.Compte;

public class CompteMapper {
    public  static CompteResponseDTO toDTO(Compte compte){
        CompteResponseDTO dto = new CompteResponseDTO();
        dto.setId(compte.getId());
        dto.setNum(compte.getNum());
        dto.setSolde(compte.getSolde());
        dto.setDateCreation(compte.getDateCreation());
        dto.setType(String.valueOf(compte.getType()));
        dto.setClient(compte.getClient());

        return dto;
    }
}
