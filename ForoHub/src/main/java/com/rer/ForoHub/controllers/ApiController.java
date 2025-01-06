package com.rer.ForoHub.controllers;

import com.rer.ForoHub.model.Respuestas;
import com.rer.ForoHub.model.Topico;
import com.rer.ForoHub.model.Usuario;
import com.rer.ForoHub.services.RespuestasService;
import com.rer.ForoHub.services.TopicoService;
import com.rer.ForoHub.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
                usuario.setId(id);
                Usuario usuarioActualizado = usuarioServ.saveUsuario(usuario);
                return ResponseEntity.ok(usuarioActualizado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/usuarios/eliminarUsuarioId/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        try {
            Optional<Usuario> usuarioExistente = usuarioServ.getUsuarioById(id);
            if (usuarioExistente.isPresent()) {
                usuarioServ.deleteUsuario(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
    public ResponseEntity<Topico> crearTopico(@RequestBody Topico topico) {
        try {
            Topico nuevoTopico = topicoServ.guardarTopico(topico);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTopico);
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








