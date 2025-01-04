package com.rer.ForoHub.controllers;

import com.rer.ForoHub.model.Topico;
import com.rer.ForoHub.model.TopicoDto;
import com.rer.ForoHub.model.tokenDTO;
import com.rer.ForoHub.security.JwtUtil;
import com.rer.ForoHub.services.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    TopicoService topicoServ;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    tokenDTO tokendto;

    @PostMapping("/crearTopico")
    @PreAuthorize("hasRole('USUARIO')")
    public ResponseEntity<Topico> createTopico(@Valid @RequestBody TopicoDto topicoDto) {
        if (jwtUtil.validarToken(tokendto.tokenDto())) {
            Topico crearTopico=new Topico(topicoDto.tituloDto(),topicoDto.mensajeDto(),topicoDto.fechacreaciontopicoDto(),
                                          topicoDto.statusDto(),topicoDto.autorDto());
             Topico topico = topicoServ.createTopico(crearTopico);
             return new ResponseEntity<>(topico, HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
/*
GET /topics: Obtener la lista de todos los tópicos.
GET /topics/{id}: Obtener un tópico específico.
POST /topics/{topicId}/replies: Crear una nueva respuesta a un tópico. (Requiere autenticación).
GET /topics/{topicId}/replies: Obtener la lista de respuestas de un tópico específico. */


