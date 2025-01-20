package com.rer.ForoHub.Errores;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AdminAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAdminAlreadyExists(AdminAlreadyExistsException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UsuarioExistenteException.class)
    public ResponseEntity<Mensaje> handleUsuarioExistenteException(UsuarioExistenteException e) {
        return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Mensaje> handleValidationExceptions(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String errorMessage = fieldError != null ? fieldError.getDefaultMessage() : "Error desconocido";
        return new ResponseEntity<>(new Mensaje(errorMessage), HttpStatus.BAD_REQUEST);
    }
}
