package com.example.bankega.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Column(nullable = false)
    private String  nom;

    @Column(nullable = false)
    private String  prenom;

    @Column(nullable = false)
    private LocalDateTime dateNais;

    @Column(nullable = false)
    private String  sexe;

    private String  adresse;

    @Column(nullable = false)
    private String  tel;


    @Column(unique = true)
    private String  courriel;

    @Column(nullable = false)
    private boolean actif = true;


    private String  nationalite;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Compte> comptes = new ArrayList<>();

}
