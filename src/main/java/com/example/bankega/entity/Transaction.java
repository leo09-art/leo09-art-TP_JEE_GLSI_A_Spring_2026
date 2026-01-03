package com.example.bankega.entity;

import com.example.bankega.enums.TypeTransaction;
import com.example.bankega.enums.TypeTransaction;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
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
    private LocalDateTime date;

    @ManyToOne()
    @JoinColumn(name = "compte_id" , nullable = false)
    private Compte compte;
}
