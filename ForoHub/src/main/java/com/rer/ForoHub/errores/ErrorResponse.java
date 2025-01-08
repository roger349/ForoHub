package com.rer.ForoHub.errores;

import com.rer.ForoHub.model.Usuario;

public class ErrorResponse extends Usuario {

    private String mensaje;

    public ErrorResponse(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}

