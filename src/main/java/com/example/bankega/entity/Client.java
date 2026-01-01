package com.example.egabank.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
    private LocalDate dateNais;

    @Column(nullable = false)
    private String  sexe;

    private String  adresse;

    @Column(unique = true)
    private String  tel;

    @Column(unique = true)
    private String  courriel;

    private String  nationalite;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Compte> comptes = new ArrayList<>();


    @OneToOne(mappedBy = "client")
    private  User user;
}
