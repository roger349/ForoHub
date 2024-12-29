package com.rer.ForoHub.controllers;

import com.rer.ForoHub.model.Topico;
import com.rer.ForoHub.services.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoServ;

    // Crear un nuevo tópico
    @PostMapping
    public ResponseEntity<Topico> createTopico(@RequestBody Topico topico) {
        Topico createdTopico = topicoServ.createTopico(topico);
        return new ResponseEntity<>(createdTopico, HttpStatus.CREATED);
    }
    // Mostrar todos los tópicos
    @GetMapping
    public ResponseEntity<List<Topico>> getAllTopicos() {
        List<Topico> topicos = topicoServ.getAllTopicos();
        return new ResponseEntity<>(topicos, HttpStatus.OK);
    }
    // Mostrar un tópico específico
    @GetMapping("/{id}")
    public ResponseEntity<Topico> getTopicoById(@PathVariable Long id) {
        Optional<Topico> topico = topicoServ.getTopicoById(id);
        return topico.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    // Actualizar un tópico
    @PutMapping("/{id}")
    public ResponseEntity<Topico> updateTopico(@PathVariable Long id, @RequestBody Topico topicoDetails) {
        Topico updatedTopico = topicoServ.updateTopico(id, topicoDetails);
        return updatedTopico != null ? new ResponseEntity<>(updatedTopico, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    // Eliminar un tópico
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopico(@PathVariable Long id) {
        topicoServ.deleteTopico(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}



