package com.example.bankega.mapper;

import com.example.bankega.dto.TransactionResponseDTO;
import com.example.bankega.entity.Transaction;

public class TransactionMapper {
    public static TransactionResponseDTO toDTO(Transaction transaction){
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setId(transaction.getId());
        dto.setMontant(transaction.getMontant());
        dto.setDate(transaction.getDate());
        dto.setType(String.valueOf(transaction.getType()));

        return dto;
    }
}
