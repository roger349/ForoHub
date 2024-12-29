package com.rer.ForoHub.services;

import com.rer.ForoHub.model.Topico;
import com.rer.ForoHub.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepo;

    // Crear un nuevo tópico
    public Topico createTopico(Topico topico) {
        return topicoRepo.save(topico);
    }
    // Mostrar todos los tópicos
    public List<Topico> getAllTopicos() {
        return topicoRepo.findAll();
    }
    // Mostrar un tópico específico
    public Optional<Topico> getTopicoById(Long id) {
        return topicoRepo.findById(id);
    }
    // Actualizar un tópico
    public Topico updateTopico(Long id, Topico topicoDetails) {
        Optional<Topico> existingTopico = topicoRepo.findById(id);
        if (existingTopico.isPresent()) {
            Topico topico = existingTopico.get();
            topico.setTitulo(topicoDetails.getTitulo());
            topico.setMensaje(topicoDetails.getMensaje());
            topico.setFecha_creacion_topico(topicoDetails.getFecha_creacion_topico());
            topico.setStatus(topicoDetails.getStatus());
            return topicoRepo.save(topico);
        }
        return null;
    }
    // Eliminar un tópico
    public void deleteTopico(Long id) {
        topicoRepo.deleteById(id);
    }
}

