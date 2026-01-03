package com.example.bankega.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IbanService {

    public  String generateIban(){

        return "EG"+ UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0,18);
    }
}
