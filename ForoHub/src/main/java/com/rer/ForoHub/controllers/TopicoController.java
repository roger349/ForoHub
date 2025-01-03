package com.rer.ForoHub.controllers;

import com.rer.ForoHub.model.Topico;
import com.rer.ForoHub.services.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topico")
public class TopicoController {

    @Autowired
    private TopicoService topicoServ;
    @PostMapping
    public ResponseEntity<Topico> createTopico(@Valid @RequestBody Topico topico) {
        Topico createdTopico = topicoServ.createTopico(topico);
        return new ResponseEntity<>(createdTopico, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Topico>> getAllTopicos() {
        List<Topico> topicos = topicoServ.getAllTopicos();
        return new ResponseEntity<>(topicos, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Topico> getTopicoById(@PathVariable Long id) {
        Optional<Topico> topico = topicoServ.getTopicoById(id);
        return topico.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Topico> updateTopico(@PathVariable Long id, @Valid @RequestBody Topico topicoDetails) {
        Topico updatedTopico = topicoServ.updateTopico(id, topicoDetails);
        return updatedTopico != null ? new ResponseEntity<>(updatedTopico, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopico(@PathVariable Long id) {
        topicoServ.deleteTopico(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}




