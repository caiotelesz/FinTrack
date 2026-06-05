package com.caio.fintrack.exception;

public class ExistsNameException extends RuntimeException {

    public ExistsNameException(String name) {
        super("Já existe uma categoria com o nome '" + name + "'");
    }
}
