package com.rer.ForoHub.Services;

import com.rer.ForoHub.Errores.Mensaje;
import com.rer.ForoHub.Errores.UsuarioExistenteException;
import com.rer.ForoHub.Models.Enum.Roles;
import com.rer.ForoHub.Models.Model.Usuario;
import com.rer.ForoHub.Repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<Mensaje> crearUsuario(Usuario usuario) {
        try {
            if (usuarioRepo.findByNombre_usuario(usuario.getNombre_usuario()) != null) {
                logger.error("Usuario {} ya existe", usuario.getNombre_usuario());
                throw new UsuarioExistenteException("El usuario ya existe");
            }
            if ("ADMIN".equals(usuario.getRol().name())) {
                usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
                usuario.setRol(Roles.ADMIN);
            }
            else {
                usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
                usuario.setRol(Roles.USUARIO);
            }
            usuarioRepo.save(usuario);
            logger.info("Usuario {} registrado correctamente", usuario.getNombre_usuario());
            return new ResponseEntity<>(new Mensaje("Usuario creado con exito"), HttpStatus.CREATED);
        }
        catch (UsuarioExistenteException ex) {
            return new ResponseEntity<>(new Mensaje(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            logger.error("Error al registrar usuario: ", e);
            return new ResponseEntity<>(new Mensaje("Error al registrar usuario"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Usuario saveUsuario(Usuario usuario) {return usuarioRepo.save(usuario);}
    public List<Usuario> getAllUsuarios() {return usuarioRepo.findAll();}
    public Optional<Usuario> getUsuarioById(java.lang.Long id) {return usuarioRepo.findById(id);}
    public void deleteUsuario(java.lang.Long id) {
        try {
            usuarioRepo.deleteById(id);
            logger.info("Usuario con ID {} eliminado correctamente", id);
        } catch (DataAccessException e) {
            logger.error("Error al eliminar usuario: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar usuario", e);
        }
    }
    public boolean existeAdministrador() {
        long count = usuarioRepo.countByRol(Roles.ADMIN);
        return count > 0;
    }
}


