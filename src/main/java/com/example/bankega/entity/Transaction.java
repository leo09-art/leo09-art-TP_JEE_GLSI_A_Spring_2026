package com.example.egabank.entity;

import com.example.egabank.enums.TypeTransaction;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Enumerated(EnumType.STRING)
    private TypeTransaction type;

    @Column(nullable = false)
    private double montant;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne()
    @JoinColumn(name = "compte_id" , nullable = false)
    private Compte compte;
}
