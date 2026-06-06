package com.caio.fintrack.exception;

public class CategoryAlreadyExistsException extends RuntimeException {

    public CategoryAlreadyExistsException(String name) {
        super("Já existe uma categoria com o nome '" + name + "'");
    }
}
