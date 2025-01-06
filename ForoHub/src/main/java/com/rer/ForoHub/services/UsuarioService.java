package com.rer.ForoHub.services;

import com.rer.ForoHub.model.Roles;
import com.rer.ForoHub.model.Usuario;
import com.rer.ForoHub.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    UsuarioRepository usuarioRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(Usuario usuario) {

          if(!usuarioRepo.exists(usuario.getRol())) {

              if (usuarioRepo.findByNombre_usuario(usuario.getNombre_usuario()) != null) {
                  logger.error("Usuario {} ya existe", usuario.getNombre_usuario());
                  throw new UsuarioExistenteException("El usuario ya existe");
              }
              if ("ADMIN".equals(usuario.getRol().name())) {
                  usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
                  usuario.setRol(Roles.ADMIN);
              } else {
                  usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
                  usuario.setRol(Roles.USUARIO);
              }
              try {
                  usuarioRepo.save(usuario);
                  logger.info("Usuario {} registrado correctamente", usuario.getNombre_usuario());
                  return usuario;
              } catch (DataAccessException e) {
                  logger.error("Error al registrar usuario: {}", e.getMessage());
                  throw new RuntimeException("Error al registrar usuario", e);
              }
          }
          else {
              logger.error("No se Puede registrar mas de un usuario {} ",usuario.getRol().name() );
              throw new UsuarioExistenteException("El ADMIN ya existe");
          }
    }
    public Usuario saveUsuario(Usuario usuario) {return usuarioRepo.save(usuario);}
    public List<Usuario> getAllUsuarios() {return usuarioRepo.findAll();}
    public Optional<Usuario> getUsuarioById(Long id) {return usuarioRepo.findById(id);}
    public Usuario updateUsuario(Long id, Usuario usuarioDetails) {return usuarioDetails;}
    public void deleteUsuario(Long id) {
        try {
            usuarioRepo.deleteById(id);
            logger.info("Usuario con ID {} eliminado correctamente", id);
        } catch (DataAccessException e) {
            logger.error("Error al eliminar usuario: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar usuario", e);
        }
    }
}


