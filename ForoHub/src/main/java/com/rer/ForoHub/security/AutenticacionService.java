package com.rer.ForoHub.security;

import com.rer.ForoHub.model.Usuario;
import com.rer.ForoHub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.Optional;

@Service
public class AutenticacionService {

        @Autowired
        private UsuarioRepository usuarioRepo;
        @Autowired
        private PasswordEncoder PE;

        public Usuario autenticarUsuario(Usuario usuario) throws AuthenticationException {
            // Buscar al usuario por su nombre de usuario
            Optional<Usuario> usuarioOptional = usuarioRepo.findByNombre(usuario.getNombre());
            if (usuarioOptional.isPresent()) {
                Usuario usuarioExistente = usuarioOptional.get();
                // Verificar si la contraseña es correcta
                if (PE.matches(usuario.getContraseña(), usuarioExistente.getContraseña())) {
                    return usuarioExistente;
                } else {
                    throw new AuthenticationException("Contraseña incorrecta");
                }
            } else {
                throw new AuthenticationException("Usuario no encontrado");
            }
        }
}






