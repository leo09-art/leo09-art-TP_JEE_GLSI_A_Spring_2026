package com.example.egabank.entity;

import com.example.egabank.enums.TypeCompte;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDate dateCreation;

    @Column(nullable = false)
    private double solde=0.0;

    @ManyToOne()
    @JoinColumn(name = "client_id" , nullable = false)
    private Client client;

    @OneToMany(mappedBy = "compte")
    private List<Transaction> transactions = new ArrayList<>();

}
