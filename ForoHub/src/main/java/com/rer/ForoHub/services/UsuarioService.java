package com.rer.ForoHub.services;

import com.rer.ForoHub.model.Usuario;
import com.rer.ForoHub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    public Usuario createUsuario(Usuario usuario) {
        return usuarioRepo.save(usuario);
    }
    public List<Usuario> getAllUsuarios() {
        return usuarioRepo.findAll();
    }
    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepo.findById(id);
    }
    public Usuario updateUsuario(Long id, Usuario usuarioDetails) {
        Optional<Usuario> existingUsuario = usuarioRepo.findById(id);
        if (existingUsuario.isPresent()) {
            Usuario usuario = existingUsuario.get();
            usuario.setNombre(usuarioDetails.getNombre());
            usuario.setCorreo_Electronico(usuarioDetails.getCorreo_Electronico());
            usuario.setContraseña(usuarioDetails.getContraseña());
            return usuarioRepo.save(usuario);
        }
        return null;
    }
    public void deleteUsuario(Long id) {
        usuarioRepo.deleteById(id);
    }
}

