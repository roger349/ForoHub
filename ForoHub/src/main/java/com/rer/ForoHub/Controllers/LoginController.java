package com.rer.ForoHub.Controllers;

import com.rer.ForoHub.Errores.AdminAlreadyExistsException;
import com.rer.ForoHub.Errores.Mensaje;
import com.rer.ForoHub.Errores.UsuarioExistenteException;
import com.rer.ForoHub.Models.Dto.LoginDto;
import com.rer.ForoHub.Models.Dto.UsuarioDto;
import com.rer.ForoHub.Models.Dto.tokenDTO;
import com.rer.ForoHub.Models.Model.Usuario;
import com.rer.ForoHub.Repository.UsuarioRepository;
import com.rer.ForoHub.Security.Authenticar;
import com.rer.ForoHub.Security.JwtUtil;
import com.rer.ForoHub.Services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:8082")
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

    @Transactional
    @PostMapping("/registrarUsuario")
    public ResponseEntity<Mensaje> registrarUsuario(@RequestBody @Valid UsuarioDto usuarioDTO) {
        try {
            boolean existeAdmin = usuarioServ.existeAdministrador();
            String rol= usuarioDTO.rolDto().name().toUpperCase();
            if (existeAdmin && "ADMIN".equals(rol)) {

                throw new AdminAlreadyExistsException("El usuario ADMIN ya existe, solo existe un ADMIN");
            }
            else {
                if (usuarioRepo.findByNombre_usuario(usuarioDTO.nombreUsuarioDto()) != null) {

                     throw new UsuarioExistenteException("El usuario ya existe, no se permite nombre de usuario repetido");
                }
                else {
                    Usuario usuario = new Usuario(usuarioDTO.contraseñaDto(),
                    usuarioDTO.nombreUsuarioDto(), usuarioDTO.correoElectronicoDto(), usuarioDTO.rolDto());
                    Usuario user = usuarioServ.crearUsuario(usuario);
                    return ResponseEntity.status(HttpStatus.CREATED).build();
                }
            }
        }
        catch (UsuarioExistenteException e) {
            return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        catch (AdminAlreadyExistsException e) {
            return new ResponseEntity<>(new Mensaje(e.getMessage()), HttpStatus.FORBIDDEN);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> autenticar(@RequestBody @Valid LoginDto loginDTO) {
        try {
            String username = loginDTO.username();
            String password = loginDTO.password();
            String jwt = authenticar.loginValidacion(username,password);
            tokenDTO token=new tokenDTO(jwt);
            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);
            response.put("username", username);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
