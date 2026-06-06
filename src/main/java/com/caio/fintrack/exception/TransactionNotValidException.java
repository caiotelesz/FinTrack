package com.caio.fintrack.exception;

public class TransactionNotValidException extends RuntimeException {

    public TransactionNotValidException() {
        super("O campo 'valor' deve ser maior que zero.");
    }
}
