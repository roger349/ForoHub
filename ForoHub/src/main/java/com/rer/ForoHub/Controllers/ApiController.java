package com.rer.ForoHub.Controllers;

import com.rer.ForoHub.Models.Dto.RespuestasDto;
import com.rer.ForoHub.Models.Dto.TopicoDto;
import com.rer.ForoHub.Models.Enum.Status;
import com.rer.ForoHub.Models.Model.Respuestas;
import com.rer.ForoHub.Models.Model.Topico;
import com.rer.ForoHub.Models.Model.TopicoRespuestas;
import com.rer.ForoHub.Models.Model.Usuario;
import com.rer.ForoHub.Repository.RespuestasRepository;
import com.rer.ForoHub.Repository.TopicoRepository;
import com.rer.ForoHub.Repository.UsuarioRepository;
import com.rer.ForoHub.Services.RespuestasService;
import com.rer.ForoHub.Services.TopicoService;
import com.rer.ForoHub.Services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    RespuestasRepository respuestasRepo;

    @Transactional(readOnly = true)
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
    @Transactional
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
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/usuarios/actualizarUsuarioId/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable long id,@RequestBody Usuario usuario) {
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
    @Transactional
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
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/topicos/listarTopicos")
    public ResponseEntity<Page<Topico>> listarTopicos(@PageableDefault(page=0, size=10,sort="fecha_creacion_topico,asc")
                                                      Pageable pageable) {
            try {
                Sort sort = JpaSort.unsafe("fecha_creacion_topico").ascending();
                Pageable pageableConSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
                Page<Topico> topicos = topicoRepo.findAllTopicoWithRespuestas(pageableConSort);
                //List<Topico> topicos = topicoServ.getAllTopicos();
            return ResponseEntity.ok(topicos);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @ResponseBody
    @GetMapping("/topicos/listarTopicoPorIdRespuestas/{id}")
    public ResponseEntity<TopicoRespuestas> listarTopicoPorIdRespuestas(@PathVariable Long id,
                                             @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
         try {
             Optional<Topico> topicoOpt = topicoRepo.findById(id);
             if (topicoOpt.isEmpty()) {
                 return ResponseEntity.notFound().build();
             } else {
                 Page<Respuestas> respuestasPage = respuestasRepo.findByTopicoId(id, pageable);
                 TopicoRespuestas topicoRespuestas = new TopicoRespuestas(respuestasPage);
                 return ResponseEntity.ok(topicoRespuestas);
             }
         }
         catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
         }
    }
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @PostMapping("/topicos/crearTopico")
    public ResponseEntity<Topico> crearTopico(@Valid @RequestBody TopicoDto topicoDto) {
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
            throw new RuntimeException("Error al crear el topico", e);
        }
    }
    @Transactional
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
    @Transactional
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
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/respuestas/listarRespuestas")
    public ResponseEntity<List<Respuestas>> listarRespuestas() {
        List<Respuestas> respuestas = respuestaServ.getAllRespuestas();
        return ResponseEntity.ok(respuestas);
    }
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @PostMapping("/respuestas/crearRespuesta/{id}")
    public ResponseEntity<Respuestas> crearRespuesta(@PathVariable long id, @Valid @RequestBody RespuestasDto respuestaDto) {
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
            throw new RuntimeException("Error al crear la Respuesta", e);
        }
    }
    @Transactional
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
    @Transactional
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








