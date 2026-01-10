package com.example.bankega.mapper;

import com.example.bankega.dto.ClientDTO;
import com.example.bankega.entity.Client;

public class ClientMapper {
    public static ClientDTO toDto(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setNom(client.getNom());
        dto.setPrenom(client.getPrenom());
        dto.setAdresse(client.getAdresse());
        dto.setSexe(client.getSexe());
        dto.setTel(client.getTel());
        dto.setNationalite(client.getNationalite());
        dto.setDateNais(client.getDateNais());
        dto.setActif(client.isActif());

        return dto;
    }

}
