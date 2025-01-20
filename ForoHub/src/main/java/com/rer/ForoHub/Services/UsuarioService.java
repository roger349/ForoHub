package com.rer.ForoHub.Services;

import com.rer.ForoHub.Models.Enum.Roles;
import com.rer.ForoHub.Models.Model.Usuario;
import com.rer.ForoHub.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Usuario crearUsuario(Usuario usuario) {

            if ("ADMIN".equals(usuario.getRol().name())) {
                usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
                usuario.setRol(Roles.ADMIN);
            }
            else {
                usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
                usuario.setRol(Roles.USUARIO);
            }
        Usuario user = usuarioRepo.save(usuario);
        return user;
    }
    public Usuario saveUsuario(Usuario usuario) {return usuarioRepo.save(usuario);}
    public List<Usuario> getAllUsuarios() {return usuarioRepo.findAll();}
    public Optional<Usuario> getUsuarioById(java.lang.Long id) {return usuarioRepo.findById(id);}
    public void deleteUsuario(java.lang.Long id) {
        try {
            usuarioRepo.deleteById(id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al eliminar usuario", e);
        }
    }
    public boolean existeAdministrador() {
        long count = usuarioRepo.countByRol(Roles.ADMIN);
        return count > 0;
    }
}


