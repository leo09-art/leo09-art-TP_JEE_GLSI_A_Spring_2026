package com.example.bankega.service;


import com.example.bankega.entity.Client;
import com.example.bankega.dto.InscriptionRequest;
import com.example.bankega.entity.User;
import com.example.bankega.repository.ClientRepository;
import com.example.bankega.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Console;

@Service
@Transactional
public class InscriptionService {

    private PasswordEncoder passwordEncoder;
    private  final ClientRepository clientRepository;
    private  final UserRepository userRepository;

    public InscriptionService(ClientRepository clientRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    public Client inscrireClient(InscriptionRequest req){


        Client client = new Client();
        client.setNom(req.getNom());
        client.setPrenom(req.getPrenom());
        client.setDateNais(req.getDateNais());
        client.setSexe(req.getSexe());
        client.setAdresse(req.getAdresse());
        client.setTel(req.getTel());
        client.setCourriel(req.getCourriel());
        client.setNationalite(req.getNationalite());
        Client createdClient  = clientRepository.save(client);


        System.out.println(req.getPassword());
        User user = new User();
        user.setUsername(req.getCourriel());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setClient(createdClient);
        userRepository.save(user);
        return createdClient ;
    }


}
