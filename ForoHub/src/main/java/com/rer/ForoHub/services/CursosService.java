package com.rer.ForoHub.services;

import com.rer.ForoHub.model.Cursos;
import com.rer.ForoHub.repository.CursosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CursosService {

    @Autowired
    private CursosRepository cursosRepo;

    public Cursos createCurso(Cursos curso) {
        return cursosRepo.save(curso);
    }
    public List<Cursos> getAllCursos() {
        return cursosRepo.findAll();
    }
    public Optional<Cursos> getCursoById(Long id) {
        return cursosRepo.findById(id);
    }
    public Cursos updateCurso(Long id, Cursos cursoDetails) {
        Optional<Cursos> existingCurso = cursosRepo.findById(id);
        if (existingCurso.isPresent()) {
            Cursos curso = existingCurso.get();
            curso.setNombre_curso(cursoDetails.getNombre_curso());
            curso.setCategoria(cursoDetails.getCategoria());
            return cursosRepo.save(curso);
        }
        return null;
    }
    public void deleteCurso(Long id) {
        cursosRepo.deleteById(id);
    }
}

