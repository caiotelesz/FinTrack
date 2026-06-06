package com.caio.fintrack.exception;

public class CategoryIdNotFoundException extends RuntimeException{

    public CategoryIdNotFoundException() {
        super("Transação não encontrada.");
    }
}
