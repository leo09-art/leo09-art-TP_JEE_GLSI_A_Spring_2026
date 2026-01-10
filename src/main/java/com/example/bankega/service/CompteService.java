package com.example.bankega.service;

import com.example.bankega.dto.CompteUpdateRequest;
import com.example.bankega.entity.Client;
import com.example.bankega.entity.Compte;
import com.example.bankega.entity.Transaction;
import com.example.bankega.enums.TypeCompte;
import com.example.bankega.enums.TypeTransaction;
import com.example.bankega.exception.ResourceNotFoundException;
import com.example.bankega.exception.UnauthorizedAccessException;
import com.example.bankega.repository.CompteRepository;
import com.example.bankega.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class CompteService {

    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    private final IbanService ibanService;

    public  CompteService(CompteRepository compteRepository, TransactionRepository transactionRepository, IbanService ibanService){
        this.compteRepository = compteRepository;
        this.transactionRepository = transactionRepository;
        this.ibanService = ibanService;
    }

    public  Compte CreerCompte(Client client, TypeCompte typeCompte){
        Compte compte = new Compte();
        compte.setClient(client);
        compte.setDateCreation(LocalDateTime.now());
        compte.setType(typeCompte);
        compte.setNum(ibanService.generateIban());
        return compteRepository.save(compte);
    }

    public Compte getCompteSecurise(String compteNum, Client client){
        Compte compte = compteRepository.findByNumAndActifTrue(compteNum)
                .orElseThrow(()-> new ResourceNotFoundException("Compte non trouve"));


        System.out.println("id client connecter: "+client.getCourriel());
        System.out.println("id client connecter: "+client.getCourriel());
        System.out.println("id client compte enovoyer : "+compte.getClient().getId());
        if(compte.getClient().getId() != client.getId()){
            throw  new UnauthorizedAccessException("Acces non autorise au compte");
        }

        return  compte;
    }

    public Compte updateCompte(
            String compteNum,
            Client client,
            CompteUpdateRequest req
    ) {
        Compte compte = getCompteSecurise(compteNum, client);

        if (req.getType() != null)
            compte.setType(req.getType());

        return compte;
    }


    public void supprimerCompte(String compteNum) {
        Compte compte = compteRepository.findByNumAndActifTrue(compteNum)
                .orElseThrow(() -> new ResourceNotFoundException("Compte introuvable"));

        System.out.println(compte.getSolde() !=0);
        if (compte.getSolde() != 0) {
            throw new RuntimeException("Impossible de supprimer un compte avec solde non nul");
        }

        compte.setActif(false);
    }



}
