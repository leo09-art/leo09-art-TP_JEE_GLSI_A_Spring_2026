package com.example.bankega.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TransactionResponseDTO {    private Long id;
    private String type;
    private double montant;
    private LocalDateTime date;
    private String compteNum;
}
