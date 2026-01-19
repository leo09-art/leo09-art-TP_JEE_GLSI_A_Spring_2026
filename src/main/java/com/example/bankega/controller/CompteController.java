package com.example.bankega.controller;

import com.example.bankega.dto.*;
import com.example.bankega.entity.Client;
import com.example.bankega.entity.Compte;
import com.example.bankega.entity.User;
import com.example.bankega.enums.TypeCompte;
import com.example.bankega.exception.ResourceNotFoundException;
import com.example.bankega.exception.UnauthorizedAccessException;
import com.example.bankega.mapper.CompteMapper;
import com.example.bankega.repository.ClientRepository;
import com.example.bankega.repository.CompteRepository;
import com.example.bankega.repository.UserRepository;
import com.example.bankega.service.CompteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/comptes")
public class CompteController {

    private final CompteService compteService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    CompteRepository compteRepository;
    ClientRepository clientRepository;
    CompteController(CompteRepository compteRepository, ClientRepository clientRepository, CompteService compteService, UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.compteRepository = compteRepository;
        this.clientRepository = clientRepository;
        this.compteService = compteService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    @GetMapping
//    public ResponseEntity<List<CompteResponseDTO>> getComptes(){
//        List<CompteResponseDTO> comptes = compteRepository
//                .findAll()
//                .stream()
//                .map(t->{
//                    return CompteMapper.toDTO(t);
//                })
//                .toList();
//        return new ResponseEntity<>(comptes, HttpStatus.OK);
//    }
    @GetMapping
    public ResponseEntity<List<CompteResponseDTO>> getClientCompte(
            Authentication authentication
//            ,@PathVariable Long id
    ){
        Client client = getClient(authentication);
        List<CompteResponseDTO> comptes = compteRepository
                .findByClientIdAndActifTrue(client.getId())
                .stream()
                .map(t->{
                    return CompteMapper.toDTO(t);
                })
                .toList();
        return new ResponseEntity<>(comptes, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CompteResponseDTO>> getClientComptes(
            Authentication authentication
//            ,@PathVariable Long id
    ){
        List<CompteResponseDTO> comptes = compteRepository
                .findByActifTrue()
                .stream()
                .map(t->{
                    return CompteMapper.toDTO(t);
                })
                .toList();
        return new ResponseEntity<>(comptes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CompteResponseDTO> createdCompte(Authentication authentication,@RequestBody CreatedCompteRequest createdCompteRequest){

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        String password =  createdCompteRequest.getPassword();
        if (!passwordEncoder.matches(
                password,
                user.getPassword()
        )) {
            throw new UnauthorizedAccessException("Mot de passe incorrect");
        }

        String type = createdCompteRequest.getAccountForm();
        String username = authentication.getName();

        Compte compte = compteService.CreerCompte(authentication,user.getClient(), TypeCompte.valueOf(type));
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

    @PostMapping("/delete")
    public ResponseEntity<String> supprimerCompte(Authentication authentication,@RequestBody DeleteRequest deleteRequest){
        Compte compte = compteRepository.findByNumAndActifTrue(deleteRequest.getNumCompte())
                .orElseThrow(()-> new ResourceNotFoundException("Compte introuvable"));
        compteService.supprimerCompte(authentication,compte,deleteRequest.getPassword());
        return new ResponseEntity<>("Compte désactivé avec succès", HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countMesComptes(Authentication authentication) {

        Client client = getClient(authentication);
        long count = compteService.nombreComptes(client);

        return ResponseEntity.ok(count);
    }



}
