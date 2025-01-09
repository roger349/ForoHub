package com.rer.ForoHub.controllers;

import com.rer.ForoHub.model.*;
import com.rer.ForoHub.services.RespuestasService;
import com.rer.ForoHub.services.TopicoService;
import com.rer.ForoHub.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/usuarios/listarUsuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioServ.getAllUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/usuarios/obtenerUsuarioId/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        try {
            Optional<Usuario> usuario = usuarioServ.getUsuarioById(id);
            return usuario.map(ResponseEntity::ok).orElseGet(() ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/usuarios/actualizarUsuarioId/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
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
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/usuarios/eliminarUsuarioId/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        try {
            Optional<Usuario> usuarioExistente = usuarioServ.getUsuarioById(id);
            if (usuarioExistente.isPresent()) {
                usuarioServ.deleteUsuario(id);
                return ResponseEntity.ok("Usuario Eliminado");    //status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/topicos/listarTopicos")
    public ResponseEntity<List<Topico>> listarTopicos() {
        try {
            List<Topico> topicos = topicoServ.getAllTopicos();
            return ResponseEntity.ok(topicos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @PostMapping("/topicos/crearTopico")
    public ResponseEntity<Topico> crearTopico(@RequestBody TopicoDto topicoDto) {
        try {
            String titulo=topicoDto.titulo();
            String mensaje=topicoDto.mensaje();
            LocalDate fechaCreacionTopico = topicoDto.fecha_creacion_topico();
            Status status = topicoDto.status();
            Categorias categoria = topicoDto.categoria();
            UsuarioDTO user = new UsuarioDTO(topicoDto.autor().getId(),topicoDto.autor().getContraseña(),
                                                topicoDto.autor().getNombre_usuario(),topicoDto.autor().getCorreo_Electronico(),
                                                topicoDto.autor().getRol());
            Topico topico = new Topico(titulo,mensaje,fechaCreacionTopico,categoria,user);
            topicoServ.guardarTopico(topico);
            return ResponseEntity.status(HttpStatus.CREATED).body(topico);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @PutMapping("/topicos/actualizarTopicoId/{id}")
    public ResponseEntity<Topico> actualizarTopico(@PathVariable Long id, @RequestBody Topico topico) {
        try {
            Optional<Topico> usuarioExistente = topicoServ.getTopicoById(id);
            if (usuarioExistente.isPresent()) {
                topico.setId(id);
                Topico topicoActualizado = topicoServ.guardarTopico(topico);
                return ResponseEntity.ok(topicoActualizado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/topicos/eliminarTopicoId/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        try {
            Optional<Topico> topicoExistente = topicoServ.getTopicoById(id);
            if (topicoExistente.isPresent()) {
                topicoServ.deleteTopico(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/respuestas/listarRespuestas")
    public ResponseEntity<List<Respuestas>> listarRespuestas() {
        try {
            List<Respuestas> respuestas = respuestaServ.getAllRespuestas();
            return ResponseEntity.ok(respuestas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @PostMapping("/respuestas/crearRespuesta")
    public ResponseEntity<Respuestas> crearRespuesta(@RequestBody Respuestas respuesta) {
        try {
            Respuestas nuevaRespuesta = respuestaServ.guardarRespuesta(respuesta);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaRespuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @PutMapping("/respuestas/actualizarRespuestaId/{id}")
    public ResponseEntity<Respuestas> actualizarRespuesta(@PathVariable Long id, @RequestBody Respuestas respuesta) {
        try {
            Optional<Respuestas> respuestaExistente = respuestaServ.getRespuestaById(id);
            if (respuestaExistente.isPresent()) {
                respuesta.setId(id);
                Respuestas respuestaActualizada = respuestaServ.guardarRespuesta(respuesta);
                return ResponseEntity.ok(respuestaActualizada);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/respuestas/eliminarRespuestaId/{id}")
    public ResponseEntity<Void> eliminarRespuesta(@PathVariable Long id) {
        try {
            Optional<Respuestas> respuestaExistente = respuestaServ.getRespuestaById(id);
            if (respuestaExistente.isPresent()) {
                respuestaServ.deleteRespuesta(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}








