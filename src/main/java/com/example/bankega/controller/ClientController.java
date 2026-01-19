package com.example.bankega.controller;

import com.example.bankega.dto.ClientDTO;
import com.example.bankega.dto.ClientUpdateRequest;
import com.example.bankega.dto.UserDTO;
import com.example.bankega.entity.Client;
import com.example.bankega.dto.InscriptionRequest;
import com.example.bankega.entity.User;
import com.example.bankega.exception.ResourceNotFoundException;
import com.example.bankega.exception.UnauthorizedAccessException;
import com.example.bankega.mapper.ClientMapper;
import com.example.bankega.mapper.UserMapper;
import com.example.bankega.repository.ClientRepository;
import com.example.bankega.repository.UserRepository;
import com.example.bankega.service.ClientService;
import com.example.bankega.service.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.stream.Stream;

@RestController
@RequestMapping("/api/auth/clients")
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    private final InscriptionService inscriptionService;
    private final ClientService clientService;

    public ClientController(InscriptionService inscriptionService, ClientService clientService,PasswordEncoder passwordEncoder){
        this.inscriptionService = inscriptionService;
        this.clientService = clientService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<Stream<ClientDTO>> getAllClients(){
        return new ResponseEntity<>(clientRepository.findAll()
                .stream().map(
                        t->{
                            return ClientMapper.toDto(t);
                        }
                ),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody InscriptionRequest inscriptionRequest){
        Client createdClient = inscriptionService.inscrireClient(inscriptionRequest);
        return new  ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Client> getClientById(@PathVariable long id){
        return new ResponseEntity<>(clientRepository.findById(id).orElse(null), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ClientDTO getCurrentClient(Authentication authentication){
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow();
        return ClientMapper.toDto(user.getClient());
    }

    @GetMapping("/user")
    public UserDTO getCurrentUser(Authentication authentication){
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new ResourceNotFoundException("utilisateur non trouvé"));
        return UserMapper.toDTO(user);
    }

    @PutMapping("/me")
    public  ClientDTO updateClient(
            Authentication authentication,
            @RequestBody ClientUpdateRequest request
    ) {

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        String password = request.getPassword();
        if (!passwordEncoder.matches(
                password,
                user.getPassword()
        )) {
            throw new UnauthorizedAccessException("Mot de passe incorrect");
        }

        Client client = getClient(authentication);
        return ClientMapper.toDto(clientService.updateClient(client, request,authentication ));
    }

    private Client getClient(Authentication authentication) {
        Client client = userRepository.findByUsername(authentication.getName())
                .orElseThrow()
                .getClient();
        return  client;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerClient(@PathVariable Long id){
        clientService.supprimerClient(id);
        return ResponseEntity.ok("Client désactivé avec succès");
    }


}
