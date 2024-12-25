package com.rer.ForoHub.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="consultas")
public class Consultas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titulo_consulta", nullable = false)
    private String titulo_consulta;
    @Column(name = "contenido_consulta", nullable = false)
    private String contenido;
    @Column(name = "fecha_consulta", nullable = false)
    private LocalDateTime fecha_consulta;
    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario user;
    @OneToMany(mappedBy = "consultas")
    private List<Respuestas> respuestas;
    // Getters y Setters
}
