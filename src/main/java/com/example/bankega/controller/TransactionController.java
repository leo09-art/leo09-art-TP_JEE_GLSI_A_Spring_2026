package com.example.bankega.controller;


import com.example.bankega.dto.TransactionResponseDTO;
import com.example.bankega.entity.Client;
import com.example.bankega.entity.Compte;
import com.example.bankega.entity.Transaction;
import com.example.bankega.mapper.TransactionMapper;
import com.example.bankega.repository.CompteRepository;
import com.example.bankega.repository.TransactionRepository;
import com.example.bankega.repository.UserRepository;
import com.example.bankega.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/transactions")
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CompteRepository compteRepository;
    @Autowired
    private UserRepository userRepository;

    private TransactionService transactionService;

    public  TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        return new ResponseEntity<>(transactionRepository.findAll(),HttpStatus.OK);

    }

    private Client getClient(Authentication authentication){
        return  userRepository.findByUsername(authentication.getName())
                .orElseThrow()
                .getClient();
    }

    @PostMapping("/versement")
    public TransactionResponseDTO versement(Authentication authentication, @RequestParam String compteNum, @RequestParam double montant){
//        return ResponseEntity.ok(Map.of("id", compteId));
        System.out.println(authentication.getName());
       return TransactionMapper.toDTO(transactionService.depot(compteNum, montant, getClient(authentication)));
    }

    @PostMapping("/retrait")
    public TransactionResponseDTO retrait(Authentication authentication,@RequestParam String compteNum, @RequestParam double montant){
       return TransactionMapper.toDTO(transactionService.retrait(compteNum, montant, getClient(authentication)));
    }

    @PostMapping("/virement")
    public ResponseEntity<String> virement(Authentication authentication,@RequestParam String sourceNum, @RequestParam String destNum, @RequestParam double montant){
        transactionService.virement(sourceNum, destNum, montant, getClient(authentication));
        return ResponseEntity.ok("Virement effectué avec succès");
    }

    @GetMapping("/histo/{compteId}")
    public  List<TransactionResponseDTO> historique(Authentication auth, @PathVariable String compteNum){
        Client client = getClient(auth);
        Compte compte = transactionService.getCompteSecurise(compteNum, client);

        return  transactionRepository.findByCompteId(compte.getId())
                .stream()
                .map(t -> {
                    TransactionResponseDTO dto = new TransactionResponseDTO();
                    dto =TransactionMapper.toDTO(t);
                    return dto;
                })
                .toList();
    }

    @GetMapping("/histo")
    public Page<TransactionResponseDTO> historique(
            Authentication authentication,
            @RequestParam String compteNum,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate start,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate end,
            Pageable pageable
    ) {
        Client client = getClient(authentication);
//        return ResponseEntity.ok(Map.of("compteId", compteId, "start", start.atStartOfDay(), "end", end.atTime(23,59,59)));
        return transactionService.historique(
                compteNum,
                start.atStartOfDay(),
                end.atTime(23,59,59),
                pageable,
                client
        );
    }
}