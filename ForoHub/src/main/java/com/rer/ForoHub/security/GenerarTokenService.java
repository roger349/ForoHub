package com.rer.ForoHub.security;

import com.rer.ForoHub.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GenerarTokenService {
    @Autowired
    private JwtConfiguracion jwtConfig;

    public String generarToken(Usuario usuario) {
        return Jwt.builder()
                .setSubject(usuario.getNombre())
                .claim("userId", usuario.getId())
                .claim("role", usuario.getRol().name()) // Aquí añadimos el role como cadena
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getTiempoExpiracion()))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getClaveSecreta()).compact();
    }
}
