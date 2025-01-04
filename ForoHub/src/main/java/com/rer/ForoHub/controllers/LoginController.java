package com.rer.ForoHub.controllers;

import com.rer.ForoHub.model.LoginDTO;
import com.rer.ForoHub.model.Usuario;
import com.rer.ForoHub.model.UsuarioDTO;
import com.rer.ForoHub.model.tokenDTO;
import com.rer.ForoHub.security.JwtUtil;
import com.rer.ForoHub.services.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class LoginController {
    @Autowired
    UsuarioService usuarioServ;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    AuthenticationManager authenticationManager;

    final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/registrarUsuario")
    public ResponseEntity<Usuario> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = new Usuario(usuarioDTO.contraseñaDto(), usuarioDTO.correoElectronicoDto(),
                    usuarioDTO.nombreUsuarioDto(), usuarioDTO.rolDto());
            Usuario user = usuarioServ.registrarUsuario(usuario);
            user.setContraseña(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody LoginDTO loginDTO) {
        try {
            String username = loginDTO.username();
            String password = loginDTO.password();
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtil.generarToken(username);
            tokenDTO token=new tokenDTO(jwt);
            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);
            response.put("username", username);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            logger.error("Credenciales inválidas para el usuario: {}", loginDTO.username(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales inválidas"));
        } catch (Exception e) {
            logger.error("Error del servidor durante la autenticación", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error del servidor"));
        }
    }
}
