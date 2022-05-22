package com.recipee.rest;

import com.recipee.rest.ResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice extends ResponseEntityExceptionHandler implements ResponseBuilder {

    @ExceptionHandler(ResponseStatusException.class)
    public void handleBadRequestRuntimeException(ResponseStatusException e, HttpServletResponse response) throws IOException {
        log.error(e.getMessage(), e);

        if (e.getStatus().equals(HttpStatus.BAD_REQUEST)) {
            var errorMessage = e.getReason();
            response.sendError(HttpStatus.BAD_REQUEST.value(), errorMessage);
        }
    }

    @ExceptionHandler(NullPointerException.class)
    public void handleNullPointerException(NullPointerException e, HttpServletResponse response) throws IOException {
        //log.error(e.getMessage(), e);


            var errorMessage = e.getMessage();
            response.sendError(HttpStatus.BAD_REQUEST.value(), errorMessage);

    }


    /*@ExceptionHandler(BindException.class)
    public Map<String, String> handleVBindException(
            BindException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "bad" );
        return errors;
    }*/
}
