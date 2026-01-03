package com.example.bankega.exception;

public class InsufficientBalanceException extends  RuntimeException{
    public InsufficientBalanceException(String message){
        super(message);
    }
}
