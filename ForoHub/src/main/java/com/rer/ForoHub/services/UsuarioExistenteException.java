package com.rer.ForoHub.services;

public class UsuarioExistenteException extends RuntimeException {
    public UsuarioExistenteException(String message) {
        super(message);
    }
}
