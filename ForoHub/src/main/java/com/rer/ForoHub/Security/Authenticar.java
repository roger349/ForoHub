package com.rer.ForoHub.Security;

import com.rer.ForoHub.Services.UserDetailsServiceUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Authenticar {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserDetailsServiceUsuario userDetailsServiceUsuario;
    @Autowired
    PasswordEncoder passwordEncoder;

    public String loginValidacion(String username, String password) {
        UserDetails userDetails = userDetailsServiceUsuario.loadUserByUsername(username);
        if (validarCredenciales(password, userDetails)) {
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String token = jwtUtil.generarToken(userDetails.getUsername(), roles);
            return token;
        }
        else {
            throw new BadCredentialsException("Credenciales inválidas");
        }
    }
    public boolean validarCredenciales(String password, UserDetails userDetails) {
        return password != null && !password.isEmpty() && userDetails != null;
    }
}



