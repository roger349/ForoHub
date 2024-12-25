package com.rer.ForoHub.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre_usuario", nullable = false, unique = true)
    private String nombre_usuario;
    @Column(name = "email_usuario", nullable = false)
    private String email_usuario;
    @OneToMany(mappedBy = "usuario")
    private List<Consultas> consulta;
    @OneToMany(mappedBy = "usuario_respuesta")
    private List<Respuestas> respuesta_usuario;
    // Getters y Setters
}

