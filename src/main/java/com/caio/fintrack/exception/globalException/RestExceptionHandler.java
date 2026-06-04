package com.caio.fintrack.exception.globalException;

import com.caio.fintrack.exception.ExistsNameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ExistsNameException.class)
    private ResponseEntity<RestErrorMessage> existsNameException(ExistsNameException exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(
                "Conflito de dados",
                HttpStatus.CONFLICT.value(),
                exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }
}
