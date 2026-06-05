package com.caio.fintrack.exception;

import java.util.UUID;

public class CategoryIdNotFound extends RuntimeException{

    public CategoryIdNotFound() {
        super("Transação não encontrada.");
    }
}
