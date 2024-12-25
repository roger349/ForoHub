package com.rer.ForoHub.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Respuestas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "contenido", nullable = false)
    private String contenido;
    @Column(name = "fecha", nullable=false)
    private LocalDateTime fecha;
    @ManyToOne
    @JoinColumn(name = "consulta_id")
    private Consultas consulta;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario_respuesta;
    // Getters y Setters
}

