package com.rer.ForoHub.controllers;

import com.rer.ForoHub.errores.AdminAlreadyExistsException;
import com.rer.ForoHub.errores.ErrorResponse;
import com.rer.ForoHub.model.*;
import com.rer.ForoHub.repository.UsuarioRepository;
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
    UsuarioRepository usuarioRepo;
    @Autowired
    UsuarioService usuarioServ;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    AuthenticationManager authenticationManager;

    final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/registrarUsuario")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {

            boolean existeAdmin = usuarioServ.existeAdministrador();
            String rol= usuarioDTO.rolDto().name().toUpperCase();
            if (existeAdmin && "ADMIN".equals(rol)) {

                throw new AdminAlreadyExistsException("El usuario con rol ADMIN ya existe.");

            } else {
                Usuario usuario = new Usuario(usuarioDTO.contraseñaDto(), usuarioDTO.correoElectronicoDto(),
                        usuarioDTO.nombreUsuarioDto(), usuarioDTO.rolDto());
                Usuario user = usuarioServ.crearUsuario(usuario);
                user.setContraseña(null);
                return ResponseEntity.status(HttpStatus.CREATED).body(user);
            }
        }
        catch (AdminAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
        catch (Exception e) {
            logger.error("Error del servidor", e);
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
