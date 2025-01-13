package com.rer.ForoHub.Services;

import com.rer.ForoHub.Models.Enum.Permisos;
import com.rer.ForoHub.Models.Enum.Roles;
import com.rer.ForoHub.Models.Model.Usuario;
import com.rer.ForoHub.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceUsuario implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByNombre_usuario(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        Roles rol = usuario.getRol();
        String encodedPassword = passwordEncoder.encode(usuario.getContraseña());

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Permisos permiso : rol.getPermisos()) {
            authorities.add(new SimpleGrantedAuthority(permiso.name()));
        }
        return new User(usuario.getNombre_usuario(), encodedPassword, authorities);
    }
}




