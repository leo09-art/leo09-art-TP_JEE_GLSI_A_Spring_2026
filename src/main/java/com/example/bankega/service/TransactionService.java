package com.example.bankega.service;

import com.example.bankega.dto.TransactionResponseDTO;
import com.example.bankega.entity.Client;
import com.example.bankega.entity.Compte;
import com.example.bankega.entity.Transaction;
import com.example.bankega.enums.TypeTransaction;
import com.example.bankega.exception.InsufficientBalanceException;
import com.example.bankega.exception.ResourceNotFoundException;
import com.example.bankega.exception.UnauthorizedAccessException;
import com.example.bankega.mapper.TransactionMapper;
import com.example.bankega.repository.CompteRepository;
import com.example.bankega.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransactionService {

    @Autowired
    CompteRepository compteRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public Compte getCompteSecurise(String compteNum, Client client){
        Compte compte = compteRepository.findByNumAndActifTrue(compteNum)
                .orElseThrow(()-> new ResourceNotFoundException("Compte non trouve"));


//        System.out.println("id client connecter: "+client.getCourriel());
//        System.out.println("id client connecter: "+client.getCourriel());
//        System.out.println("id client compte enovoyer : "+compte.getClient().getId());
        if(compte.getClient().getId() != client.getId()){
            throw  new UnauthorizedAccessException("Acces non autorise au compte");
        }

        return  compte;
    }
    public Transaction depot(String compteNum, double montant, Client client)
    {
        Compte compte = getCompteSecurise(compteNum, client);

        compte.setSolde(compte.getSolde() + montant);

        Transaction transaction = new Transaction();

        transaction.setCompte(compte);
        transaction.setType(TypeTransaction.valueOf("DEPOT"));
        transaction.setMontant(montant);
        transaction.setDate(LocalDateTime.now());

        return  transactionRepository.save(transaction);
    }

    public  Transaction retrait(String compteNum, double montant, Client client)
    {
        Compte compte = getCompteSecurise(compteNum, client);

        if(compte.getSolde() < montant){
            throw new InsufficientBalanceException("Solde insuffisant pour le retrait");
        }

        compte.setSolde(compte.getSolde() - montant);

        Transaction transaction = new Transaction();

        transaction.setCompte(compte);
        transaction.setType(TypeTransaction.valueOf("RETRAIT"));
        transaction.setMontant(montant);
        transaction.setDate(LocalDateTime.now());

        return  transactionRepository.save(transaction);
    }

    public void virement(String compteSourceNum, String compteDestinationNum, double montant, Client client)
    {
        Compte source = getCompteSecurise(compteSourceNum, client);
        if(source.getSolde() < montant){
            throw  new InsufficientBalanceException("Solde insuffisant pour le virement");
        }

        Compte dest = compteRepository.findByNumAndActifTrue(compteDestinationNum)
                .orElseThrow(()->new ResourceNotFoundException("Compte de destination non trouve"));

        source.setSolde(source.getSolde() - montant);
        dest.setSolde(dest.getSolde() + montant);

        transactionRepository.save(newTransaction( source,montant, TypeTransaction.valueOf("VIREMENT_DEBIT")));
        transactionRepository.save(newTransaction(dest,montant, TypeTransaction.valueOf("VIREMENT_CREDIT")));

    }

    private Transaction newTransaction(Compte compte, double montant, TypeTransaction type){
        Transaction transaction = new Transaction();
        transaction.setCompte(compte);
        transaction.setMontant(montant);
        transaction.setDate(LocalDateTime.now());
        transaction.setType(type);

        return  transaction;
    }

    public Page<TransactionResponseDTO> historique(
            String compteNum,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable,
            Client client
    ){
        Compte compte = getCompteSecurise(compteNum,client);
        return transactionRepository.findByCompteIdAndDateBetween(
                compte.getId(),
                start,
                end,
                pageable
        ).map(TransactionMapper::toDTO);
    }

    public List<Transaction> getTransactionsClient(Client client) {
        return transactionRepository.findByCompte_Client_Id(client.getId());
    }
}
