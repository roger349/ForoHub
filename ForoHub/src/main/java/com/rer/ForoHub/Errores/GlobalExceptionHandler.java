package com.rer.ForoHub.Errores;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AdminAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAdminAlreadyExists(AdminAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Datos mal ingresados, verifique el formato de los datos ingresados ");
    }
    @ExceptionHandler(UsuarioExistenteException.class)
    public ResponseEntity<Mensaje> handleUsuarioExistenteException(UsuarioExistenteException ex) {
        return new ResponseEntity<>(new Mensaje(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Mensaje> handleGenericException(Exception ex) {
        return new ResponseEntity<>(new Mensaje("Error interno del servidor"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
