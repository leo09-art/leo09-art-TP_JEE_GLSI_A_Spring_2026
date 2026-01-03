package com.example.bankega.controller;

import com.example.bankega.component.JwtUtil;
import com.example.bankega.dto.ClientDTO;
import com.example.bankega.dto.ClientUpdateRequest;
import com.example.bankega.dto.LoginRequest;
import com.example.bankega.entity.Client;
import com.example.bankega.dto.InscriptionRequest;
import com.example.bankega.entity.User;
import com.example.bankega.mapper.ClientMapper;
import com.example.bankega.repository.ClientRepository;
import com.example.bankega.repository.UserRepository;
import com.example.bankega.service.ClientService;
import com.example.bankega.service.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/clients")
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    UserRepository userRepository;

    private final InscriptionService inscriptionService;
    private final ClientService clientService;

    public ClientController(InscriptionService inscriptionService, ClientService clientService){
        this.inscriptionService = inscriptionService;
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients(){
        return new ResponseEntity<>(clientRepository.findAll(),HttpStatus.OK);
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

    @PutMapping("/me")
    public  ClientDTO updateClient(
            Authentication authentication,
            @RequestBody ClientUpdateRequest request
    ) {
        Client client = getClient(authentication);
        return ClientMapper.toDto(clientService.updateClient(client, request,authentication ));
    }

    private Client getClient(Authentication authentication) {
        Client client = userRepository.findByUsername(authentication.getName())
                .orElseThrow()
                .getClient();
        return  client;
    }

}
