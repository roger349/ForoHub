package com.rer.ForoHub.Repository;

import com.rer.ForoHub.Models.Model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Topico findById(long id);
    @Query("select t from Topico t left join fetch t.respuestas")
    Page<Topico> findAllTopicoWithRespuestas(Pageable pageable);
}



