package org.example.backend.controller.advice;

import org.example.backend.exception.BadReqException;
import org.example.backend.exception.ConflictException;
import org.example.backend.exception.InternalServerErrorException;
import org.example.backend.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        return handleException(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> handleConflictException(ConflictException e) {
        return handleException(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadReqException.class)
    public ResponseEntity<?> handleBadReqException(BadReqException e) {
        return handleException(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<?> handleInternalServerErrorException(InternalServerErrorException e) {
        return handleException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity<?> handleException(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(new HashMap<String, String>() {{
            put("message", message);
        }});
    }
}
