package com.rer.ForoHub.services;
import com.rer.ForoHub.model.Usuario;
import com.rer.ForoHub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceUsuario implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByNombre_usuario(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        User.UserBuilder builder = User.withUsername(username);
        builder.password(usuario.getContraseña());
        builder.roles(usuario.getRol().name());
        return builder.build();
    }
}



