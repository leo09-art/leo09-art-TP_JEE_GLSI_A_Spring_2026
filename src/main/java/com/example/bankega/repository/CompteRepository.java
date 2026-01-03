package com.example.bankega.repository;

import com.example.bankega.entity.Client;
import com.example.bankega.entity.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompteRepository extends JpaRepository<Compte, Long> {
    List<Compte> findByClient(Client client);
    Optional<Compte> findByNum(String num);
}
