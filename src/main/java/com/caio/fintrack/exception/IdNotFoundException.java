package com.caio.fintrack.exception;

public class IdNotFoundException extends RuntimeException{

    public IdNotFoundException() {
        super("Transação não encontrada.");
    }
}
