package com.example.bankega.controller;

import com.example.bankega.dto.CompteRequest;
import com.example.bankega.dto.CompteResponseDTO;
import com.example.bankega.dto.CompteUpdateRequest;
import com.example.bankega.dto.TransactionResponseDTO;
import com.example.bankega.entity.Client;
import com.example.bankega.entity.Compte;
import com.example.bankega.entity.User;
import com.example.bankega.enums.TypeCompte;
import com.example.bankega.mapper.CompteMapper;
import com.example.bankega.repository.ClientRepository;
import com.example.bankega.repository.CompteRepository;
import com.example.bankega.repository.UserRepository;
import com.example.bankega.service.CompteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/comptes")
public class CompteController {

    private final CompteService compteService;
    private final UserRepository userRepository;
    CompteRepository compteRepository;
    ClientRepository clientRepository;
    CompteController(CompteRepository compteRepository, ClientRepository clientRepository, CompteService compteService, UserRepository userRepository){
        this.compteRepository = compteRepository;
        this.clientRepository = clientRepository;
        this.compteService = compteService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<CompteResponseDTO>> getComptes(){
        List<CompteResponseDTO> comptes = compteRepository
                .findAll()
                .stream()
                .map(t->{
                    return CompteMapper.toDTO(t);
                })
                .toList();
        return new ResponseEntity<>(comptes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CompteResponseDTO> createdCompte(Authentication authentication,@RequestParam String type){
        String username = authentication.getName();

        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("utilisateur non trouvé")));
        Compte compte = compteService.CreerCompte(user.get().getClient(), TypeCompte.valueOf(type));
    return new ResponseEntity<>(CompteMapper.toDTO(compte), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public CompteResponseDTO  updateCompte(
            Authentication authentication,
            @RequestParam String compteNum,
            @RequestBody CompteUpdateRequest request
    ) {
        Client client = getClient(authentication);

        return CompteMapper.toDTO(
                compteService.updateCompte(compteNum, client, request)
        );
    }

    private Client getClient(Authentication authentication) {
        Client client = userRepository.findByUsername(authentication.getName())
                .orElseThrow()
                .getClient();
        return  client;
    }

}
