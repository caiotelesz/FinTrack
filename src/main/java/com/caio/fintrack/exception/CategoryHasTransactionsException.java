package com.caio.fintrack.exception;

public class CategoryHasTransactionsException extends RuntimeException {

    public CategoryHasTransactionsException() {
        super("Categoria possui transações vinculadas e não pode ser removida.");
    }
}