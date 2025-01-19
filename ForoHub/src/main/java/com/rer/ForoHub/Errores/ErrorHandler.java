package com.rer.ForoHub.Errores;

import jakarta.validation.ConstraintViolationException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> manejarErroresValidacion(ConstraintViolationException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getConstraintViolations().forEach(error -> {
            String campo = ((PathImpl) error.getPropertyPath()).getLeafNode().getName();
            String mensaje = error.getMessage();
            errores.put(campo, mensaje);
        });
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}
