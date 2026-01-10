package com.example.bankega.service;

import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IbanService {


    public String generateIban() {
        return Iban.random(CountryCode.FR).toString();
    }}

