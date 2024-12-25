package com.rer.ForoHub.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="topico")
public class Topico {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(name = "nombre_topico", nullable = false)
        private String nombre_topico;
        @Column(name = "descripcion_topico", nullable = false)
        private String descripcion_topico;
        @OneToMany(mappedBy = "topico")
        private List<Consultas> consultas;

}
