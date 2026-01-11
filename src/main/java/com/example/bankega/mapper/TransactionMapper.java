package com.example.bankega.mapper;

import com.example.bankega.dto.TransactionResponseDTO;
import com.example.bankega.entity.Transaction;

public class TransactionMapper {
    public static TransactionResponseDTO toDTO(Transaction t){
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setId(t.getId());
        dto.setType(t.getType().name());
        dto.setMontant(t.getMontant());
        dto.setDate(t.getDate());
        dto.setCompteNum(t.getCompte().getNum());
        return dto;
    }
}
