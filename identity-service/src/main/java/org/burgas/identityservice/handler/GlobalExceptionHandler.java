package org.burgas.identityservice.handler;

import org.burgas.identityservice.exception.AuthorityNotFoundException;
import org.burgas.identityservice.exception.IdentityNotFoundException;
import org.burgas.identityservice.exception.NotAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthorityNotFoundException.class)
    public ResponseEntity<String> handleAuthorityNotFoundException(AuthorityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(IdentityNotFoundException.class)
    public ResponseEntity<String> handleIdentityNotFoundException(IdentityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<String> handleNotAuthorizedException(NotAuthorizedException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }
}
