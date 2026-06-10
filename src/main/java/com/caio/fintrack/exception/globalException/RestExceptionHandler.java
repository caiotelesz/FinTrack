package com.caio.fintrack.exception.globalException;

import com.caio.fintrack.exception.IdNotFoundException;
import com.caio.fintrack.exception.CategoryAlreadyExistsException;
import com.caio.fintrack.exception.InvalidTransactionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    private ResponseEntity<RestErrorMessage> existsNameException(CategoryAlreadyExistsException exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(
                "Conflito de dados",
                HttpStatus.CONFLICT.value(),
                exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    @ExceptionHandler(IdNotFoundException.class)
    private ResponseEntity<RestErrorMessage> categoryIdNotFoundException(IdNotFoundException exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(
                "Recurso não encontrado",
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(InvalidTransactionException.class)
    private ResponseEntity<RestErrorMessage> transactionNotValidException(InvalidTransactionException exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(
                "Dados inválidos",
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }
}
