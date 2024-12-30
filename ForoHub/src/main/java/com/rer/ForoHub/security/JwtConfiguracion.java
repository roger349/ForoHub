package com.rer.ForoHub.security;

import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguracion {

    private String claveSecreta = "clave-secreta";
    private long tiempoExpiracion = 86400000;

    public String getClaveSecreta() {return claveSecreta;}
    public long getTiempoExpiracion() {return tiempoExpiracion;}
}


