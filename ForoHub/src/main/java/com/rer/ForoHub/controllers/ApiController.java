package com.rer.ForoHub.controllers;

import com.rer.ForoHub.model.*;
import com.rer.ForoHub.model.Usuario;
import com.rer.ForoHub.repository.TopicoRepository;
import com.rer.ForoHub.repository.UsuarioRepository;
import com.rer.ForoHub.services.RespuestasService;
import com.rer.ForoHub.services.TopicoService;
import com.rer.ForoHub.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class ApiController {
    @Autowired
    UsuarioService usuarioServ;
    @Autowired
    TopicoService topicoServ;
    @Autowired
    RespuestasService respuestaServ;
    @Autowired
    UsuarioRepository usuarioRepo;
    @Autowired
    TopicoRepository topicoRepo;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/usuarios/listarUsuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioServ.getAllUsuarios();
            return ResponseEntity.ok(usuarios);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/usuarios/obtenerUsuarioId/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable long id) {
        try {
            Optional<Usuario> usuario = usuarioServ.getUsuarioById(id);
            return usuario.map(ResponseEntity::ok).orElseGet(() ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/usuarios/actualizarUsuarioId/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable long id, @RequestBody Usuario usuario) {
        try {
            Optional<Usuario> usuarioExistente = usuarioServ.getUsuarioById(id);
            if (usuarioExistente.isPresent()) {
                Usuario usuarioActual = usuarioExistente.get();
                if (usuario.getContraseña() != null) {usuarioActual.setContraseña(usuario.getContraseña());}
                if (usuario.getNombre_usuario() != null) {usuarioActual.setNombre_usuario(usuario.getNombre_usuario());}
                if (usuario.getCorreo_Electronico() != null) {usuarioActual.setCorreo_Electronico(usuario.getCorreo_Electronico());}
                if (usuario.getRol() != null) {usuarioActual.setRol(usuario.getRol());}
                Usuario usuarioActualizado = usuarioServ.saveUsuario(usuarioActual);
                return ResponseEntity.ok(usuarioActualizado);
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/usuarios/eliminarUsuarioId/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable long id) {
        try {
            Optional<Usuario> usuarioExistente = usuarioServ.getUsuarioById(id);
            if (usuarioExistente.isPresent()) {
                usuarioServ.deleteUsuario(id);
                return ResponseEntity.ok("Usuario Eliminado");
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/topicos/listarTopicos")
    public ResponseEntity<List<Topico>> listarTopicos() {
        try {
            List<Topico> topicos = topicoServ.getAllTopicos();
            return ResponseEntity.ok(topicos);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @PostMapping("/topicos/crearTopico")
    public ResponseEntity<Topico> crearTopico(@RequestBody TopicoDto topicoDto) {
        try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            String username = authentication.getName();
            Usuario user = usuarioRepo.findByNombre_usuario(username);
            Topico nuevoTopico=new Topico();
            nuevoTopico.setTitulo(topicoDto.titulo());
            nuevoTopico.setMensaje(topicoDto.mensaje());
            nuevoTopico.setFecha_creacion_topico(topicoDto.fecha_creacion_topico());
            nuevoTopico.setStatus(Status.noResuelto);
            nuevoTopico.setCategoria(topicoDto.categoria());
            nuevoTopico.setAutor(user);
            topicoServ.guardarTopico(nuevoTopico);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTopico);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @PutMapping("/topicos/actualizarTopicoId/{id}")
    public ResponseEntity<Topico> actualizarTopico(@PathVariable long id, @RequestBody TopicoDto topicoDto) {
        try {
            Optional<Topico> topicoExistente = topicoServ.getTopicoById(id);
            if (topicoExistente.isPresent()) {
                Topico topicoActual = topicoExistente.get();
                if (topicoDto.titulo() != null) {topicoActual.setTitulo(topicoDto.titulo());}
                if (topicoDto.mensaje() != null) {topicoActual.setMensaje(topicoDto.mensaje());}
                if (topicoDto.fecha_creacion_topico() != null) {topicoActual.setFecha_creacion_topico(topicoDto.fecha_creacion_topico());}
                if (topicoDto.categoria() != null) {topicoActual.setCategoria(topicoDto.categoria());}
                Topico topicoActualizado = topicoServ.guardarTopico(topicoActual);
                return ResponseEntity.ok(topicoActualizado);
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/topicos/eliminarTopicoId/{id}")
    public ResponseEntity<String> eliminarTopico(@PathVariable long id) {
        try {
            Optional<Topico> topicoExistente = topicoServ.getTopicoById(id);
            if (topicoExistente.isPresent()) {
                topicoServ.deleteTopico(id);
                return ResponseEntity.ok("Topico Eliminado");
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/respuestas/listarRespuestas")
    public ResponseEntity<List<Respuestas>> listarRespuestas() {
        try {
            List<Respuestas> respuestas = respuestaServ.getAllRespuestas();
            return ResponseEntity.ok(respuestas);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @PostMapping("/respuestas/crearRespuesta/{id}")
    public ResponseEntity<Respuestas> crearRespuesta(@PathVariable long id, @RequestBody RespuestasDto respuestaDto) {
        try {
            Topico topico = topicoRepo.findById(id);
            topico.setStatus(Status.Resuelto);
            Respuestas nuevaRespuesta=new Respuestas();
            nuevaRespuesta.setMensaje_respuestas(respuestaDto.mensajeRespuesta());
            nuevaRespuesta.setFecha_creacion_respuestas(respuestaDto.fechacreacionrespuesta());
            nuevaRespuesta.setEstado(respuestaDto.estadoRespuesta());
            nuevaRespuesta.setTopico(topico);
            respuestaServ.guardarRespuesta(nuevaRespuesta);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaRespuesta);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @PutMapping("/respuestas/actualizarRespuestaId/{id}")
    public ResponseEntity<Respuestas> actualizarRespuesta(@PathVariable long id, @RequestBody RespuestasDto respuestaDto) {
        try {
            Optional<Respuestas> respuestaExistente = respuestaServ.getRespuestaById(id);
            if (respuestaExistente.isPresent()) {
                Respuestas respuestaActual = respuestaExistente.get();
                if (respuestaDto.mensajeRespuesta() != null) {
                       respuestaActual.setMensaje_respuestas(respuestaDto.mensajeRespuesta());}
                if (respuestaDto.fechacreacionrespuesta() != null) {
                       respuestaActual.setFecha_creacion_respuestas(respuestaDto.fechacreacionrespuesta());}
                if (respuestaDto.estadoRespuesta() != null) {
                       respuestaActual.setEstado(respuestaDto.estadoRespuesta());}
                Respuestas respuestaActualizada = respuestaServ.guardarRespuesta(respuestaActual);
                return ResponseEntity.ok(respuestaActualizada);
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/respuestas/eliminarRespuestaId/{id}")
    public ResponseEntity<String> eliminarRespuesta(@PathVariable long id) {
        try {
            Optional<Respuestas> respuestaExistente = respuestaServ.getRespuestaById(id);
            if (respuestaExistente.isPresent()) {
                respuestaServ.deleteRespuesta(id);
                return ResponseEntity.ok("Respuesta Eliminada");
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}








