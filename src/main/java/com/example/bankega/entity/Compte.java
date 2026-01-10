package com.example.bankega.entity;

import com.example.bankega.enums.TypeCompte;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "comptes")
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String num;

    @Enumerated(EnumType.STRING)
    private TypeCompte type;

    @Column(nullable = false)
    private LocalDateTime dateCreation;

    @Column(nullable = false)
    private double solde=0.0;

    @Column(nullable = false)
    private boolean actif = true;


    @ManyToOne()
    @JoinColumn(name = "client_id" , nullable = false)
    private Client client;

}
