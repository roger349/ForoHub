package com.rer.ForoHub.Repository;

import com.rer.ForoHub.Models.Model.Respuestas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespuestasRepository extends JpaRepository<Respuestas, Long> {
    Page<Respuestas> findByTopicoId(Long topicoId, Pageable pageable);
}
