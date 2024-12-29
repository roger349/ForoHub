package com.rer.ForoHub.services;

import com.rer.ForoHub.model.Respuestas;
import com.rer.ForoHub.repository.RespuestasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RespuestasService {

    @Autowired
    private RespuestasRepository respuestasRepo;

    public Respuestas createRespuesta(Respuestas respuesta) {
        return respuestasRepo.save(respuesta);
    }
    public List<Respuestas> getAllRespuestas() {
        return respuestasRepo.findAll();
    }
    public Optional<Respuestas> getRespuestaById(Long id) {
        return respuestasRepo.findById(id);
    }
    public Respuestas updateRespuesta(Long id, Respuestas respuestaDetails) {
        Optional<Respuestas> existingRespuesta = respuestasRepo.findById(id);
        if (existingRespuesta.isPresent()) {
            Respuestas respuesta = existingRespuesta.get();
            respuesta.setMensaje_respuestas(respuestaDetails.getMensaje_respuestas());
            respuesta.setFecha_creacion_respuestas(respuestaDetails.getFecha_creacion_respuestas());
            respuesta.setSolucion(respuestaDetails.getSolucion());
            return respuestasRepo.save(respuesta);
        }
        return null;
    }
    public void deleteRespuesta(Long id) {
        respuestasRepo.deleteById(id);
    }
}

