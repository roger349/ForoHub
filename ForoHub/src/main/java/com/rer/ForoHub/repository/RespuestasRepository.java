package com.rer.ForoHub.repository;

import com.rer.ForoHub.model.Respuestas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespuestasRepository extends JpaRepository<Respuestas, Long> {
}
