package com.rer.ForoHub.controllers;

import com.rer.ForoHub.model.Cursos;
import com.rer.ForoHub.services.CursosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cursos")
public class CursosController {

    @Autowired
    private CursosService cursosServ;

    @PostMapping
    public ResponseEntity<Cursos> createCurso(@RequestBody Cursos curso) {
        Cursos createdCurso = cursosServ.createCurso(curso);
        return new ResponseEntity<>(createdCurso, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Cursos>> getAllCursos() {
        List<Cursos> cursos = cursosServ.getAllCursos();
        return new ResponseEntity<>(cursos, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Cursos> getCursoById(@PathVariable Long id) {
        Optional<Cursos> curso = cursosServ.getCursoById(id);
        return curso.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Cursos> updateCurso(@PathVariable Long id, @RequestBody Cursos cursoDetails) {
        Cursos updatedCurso = cursosServ.updateCurso(id, cursoDetails);
        return updatedCurso != null ? new ResponseEntity<>(updatedCurso, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
        cursosServ.deleteCurso(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

