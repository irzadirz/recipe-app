package com.recipee.rest;

import com.recipee.activity.services.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice extends ResponseEntityExceptionHandler implements ResponseBuilder {

    @ExceptionHandler({InvalidRequestException.class, IllegalArgumentException.class})
    public ResponseEntity<?> handleInvalidRequestException(InvalidRequestException e, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        Response response1 = new Response<Object>(e.getMessage(),null, HttpStatus.BAD_REQUEST.toString() );
        return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException e, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        Response response1 = new Response<Object>(e.getMessage(),null, HttpStatus.BAD_REQUEST.toString() );
        return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);
    }
}
