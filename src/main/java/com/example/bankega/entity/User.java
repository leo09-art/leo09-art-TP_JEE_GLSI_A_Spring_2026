package com.example.bankega.entity;

import com.example.bankega.enums.Role;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Column(nullable = false, unique = true)
    private  String username;

    @Column(nullable = false)
    private  String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne()
    @JoinColumn(name = "client_id", nullable = false)
    private  Client client;

}
