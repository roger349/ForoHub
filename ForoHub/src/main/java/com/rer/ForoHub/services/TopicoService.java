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
    TopicoRepository topicoRepo;

    public Topico guardarTopico(Topico topico) {return topicoRepo.save(topico);}
    public List<Topico> getAllTopicos() {return topicoRepo.findAll();}
    public Optional<Topico> getTopicoById(Long id) {return topicoRepo.findById(id);}
    public void deleteTopico(Long id) {topicoRepo.deleteById(id);}
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
}

