package com.example.bankega.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TransactionResponseDTO {
    private Long id;
    private double montant;
    private String type;
    private LocalDateTime date;
}
