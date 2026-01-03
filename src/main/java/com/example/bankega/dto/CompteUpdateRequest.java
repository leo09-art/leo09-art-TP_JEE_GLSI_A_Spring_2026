package com.example.bankega.dto;

import com.example.bankega.enums.TypeCompte;
import lombok.Data;

@Data
public class CompteUpdateRequest {
    private TypeCompte type;
}
