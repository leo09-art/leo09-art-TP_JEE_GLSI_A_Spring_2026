package com.example.bankega.service;

import com.example.bankega.dto.ClientUpdateRequest;
import com.example.bankega.entity.Client;
import com.example.bankega.entity.Compte;
import com.example.bankega.entity.User;
import com.example.bankega.exception.ResourceNotFoundException;
import com.example.bankega.repository.ClientRepository;
import com.example.bankega.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ClientService {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    UserRepository userRepository;
    public Client updateClient(Client clientConnecte, ClientUpdateRequest req, Authentication authentication) {

        Client client = clientRepository.findById(clientConnecte.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Client introuvable"));

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable"));

        if (req.getAdresse() != null)
            client.setAdresse(req.getAdresse());

        if (req.getTel() != null)
            client.setTel(req.getTel());
        return client;
    }

    @Transactional
    public void supprimerClient(Long clientId) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client introuvable"));

        boolean hasCompteActif = client.getComptes()
                .stream()
                .anyMatch(Compte::isActif);

        if (hasCompteActif) {
            throw new RuntimeException("Le client possède encore des comptes actifs");
        }

        client.setActif(false);
    }

}
