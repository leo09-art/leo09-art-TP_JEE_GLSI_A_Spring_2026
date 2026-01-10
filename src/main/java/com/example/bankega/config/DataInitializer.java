package com.example.bankega.config;

import com.example.bankega.entity.Client;
import com.example.bankega.entity.Compte;
import com.example.bankega.entity.User;
import com.example.bankega.enums.Role;
import com.example.bankega.enums.TypeCompte;
import com.example.bankega.repository.ClientRepository;
import com.example.bankega.repository.CompteRepository;
import com.example.bankega.repository.UserRepository;
import com.example.bankega.service.IbanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component

public class DataInitializer implements CommandLineRunner {
        @Autowired
        UserRepository userRepository;

        @Autowired
        ClientRepository clientRepository;

        @Autowired
        private CompteRepository compteRepository;

        private final IbanService ibanService;
        private final PasswordEncoder passwordEncoder;


    public DataInitializer(
            IbanService ibanService, PasswordEncoder passwordEncoder
        ) {
        this.ibanService = ibanService;
        this.passwordEncoder = passwordEncoder;
        }

        @Override
        public void run(String... args) {

            // Vérifier si admin existe
            if (userRepository.existsByUsername("admin@bank.com")) {
                return;
            }

            // 1️⃣ Créer le client admin
            Client adminClient = new Client();
            adminClient.setNom("ADMIN");
            adminClient.setPrenom("SYSTEM");
            adminClient.setCourriel("admin@bank.com");
            adminClient.setTel("00000000");
            adminClient.setSexe("M");
            adminClient.setNationalite("SYSTEM");
            adminClient.setDateNais(LocalDateTime.now());
            adminClient.setAdresse("SYSTEM");

            clientRepository.save(adminClient);

            // 2️⃣ Créer l'utilisateur admin
            User adminUser = new User();
            adminUser.setUsername("admin@bank.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setRole(Role.ADMIN);
            adminUser.setClient(adminClient);

            userRepository.save(adminUser);

            Compte adminCompte = new Compte();
            adminCompte.setSolde(0.0);
            adminCompte.setNum(ibanService.generateIban());
            adminCompte.setClient(adminClient);
            adminCompte.setActif(true);
            adminCompte.setType(TypeCompte.valueOf("COURANT"));
            adminCompte.setDateCreation(LocalDateTime.now());

            compteRepository.save(adminCompte);

            System.out.println("✅ Admin créé automatiquement");
        }
    }


