package com.rer.ForoHub.Controllers;

import com.rer.ForoHub.Errores.AdminAlreadyExistsException;
import com.rer.ForoHub.Errores.ErrorResponse;
import com.rer.ForoHub.Models.Dto.LoginDTO;
import com.rer.ForoHub.Models.Dto.UsuarioDTO;
import com.rer.ForoHub.Models.Dto.tokenDTO;
import com.rer.ForoHub.Models.Model.Usuario;
import com.rer.ForoHub.Repository.UsuarioRepository;
import com.rer.ForoHub.Security.Authenticar;
import com.rer.ForoHub.Security.JwtUtil;
import com.rer.ForoHub.Services.UsuarioService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class LoginController {
    @Autowired
    Authenticar authenticar;
    @Autowired
    UsuarioRepository usuarioRepo;
    @Autowired
    UsuarioService usuarioServ;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    AuthenticationManager authenticationManager;

    final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Transactional
    @PostMapping("/registrarUsuario")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {

            boolean existeAdmin = usuarioServ.existeAdministrador();
            String rol= usuarioDTO.rolDto().name().toUpperCase();
            if (existeAdmin && "ADMIN".equals(rol)) {

                throw new AdminAlreadyExistsException("El usuario con rol ADMIN ya existe.");

            } else {
                Usuario usuario = new Usuario(usuarioDTO.contraseñaDto(),
                        usuarioDTO.nombreUsuarioDto(),usuarioDTO.correoElectronicoDto(), usuarioDTO.rolDto());
                Usuario user = usuarioServ.crearUsuario(usuario);
                return ResponseEntity.status(HttpStatus.CREATED).body(user);
            }
        }
        catch (AdminAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
        catch (Exception e) {
            logger.error("Error del servidor", e);
            throw new RuntimeException("Error al crear el Usuario", e);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody LoginDTO loginDTO) {
        try {
            String username = loginDTO.username();
            String password = loginDTO.password();
            String jwt = authenticar.loginValidacion(username,password);
            tokenDTO token=new tokenDTO(jwt);
            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);
            response.put("username", username);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            logger.error("Error del servidor durante la autenticación", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error del servidor"));
        }
    }
}
