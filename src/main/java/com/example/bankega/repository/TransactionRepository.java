package com.example.bankega.repository;

import com.example.bankega.entity.Compte;
import com.example.bankega.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findByCompteIdAndDateBetween(Long compteId, LocalDateTime start, LocalDateTime end,Pageable pageable
    );
    List<Transaction> findByCompteId(Long compteId);

}
