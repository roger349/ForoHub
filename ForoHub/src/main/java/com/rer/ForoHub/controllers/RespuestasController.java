package com.rer.ForoHub.controllers;

import com.rer.ForoHub.model.Respuestas;
import com.rer.ForoHub.services.RespuestasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/respuestas")
public class RespuestasController {

    @Autowired
    private RespuestasService respuestasServ;

    @PostMapping
    public ResponseEntity<Respuestas> createRespuesta(@RequestBody Respuestas respuesta) {
        Respuestas createdRespuesta = respuestasServ.createRespuesta(respuesta);
        return new ResponseEntity<>(createdRespuesta, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Respuestas>> getAllRespuestas() {
        List<Respuestas> respuestas = respuestasServ.getAllRespuestas();
        return new ResponseEntity<>(respuestas, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Respuestas> getRespuestaById(@PathVariable Long id) {
        Optional<Respuestas> respuesta = respuestasServ.getRespuestaById(id);
        return respuesta.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Respuestas> updateRespuesta(@PathVariable Long id, @RequestBody Respuestas respuestaDetails) {
        Respuestas updatedRespuesta = respuestasServ.updateRespuesta(id, respuestaDetails);
        return updatedRespuesta != null ? new ResponseEntity<>(updatedRespuesta, HttpStatus.OK): new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}