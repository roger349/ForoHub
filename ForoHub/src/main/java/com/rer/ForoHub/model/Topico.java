package com.rer.ForoHub.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "topico")
@AllArgsConstructor
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "mensaje")
    private String mensaje;
    @Column(name = "fecha_creacion_topico")
    private LocalDate fecha_creacion_topico;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name="categoria")
    @Enumerated(EnumType.STRING)
    private Categorias categoria;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    @JsonManagedReference
    private Usuario autor;
    @OneToMany(mappedBy = "topico")
    private List<Respuestas> respuestas;

    public Topico(){
    }

    public Topico(String titulo, String mensaje, LocalDate fecha_creacion_topico,
                  Status status, Categorias categoria , Usuario autor) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fecha_creacion_topico = fecha_creacion_topico;
        this.status = status;
        this.categoria=categoria;
        this.autor = autor;
    }

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}
    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}
    public String getMensaje() {return mensaje;}
    public void setMensaje(String mensaje) {this.mensaje = mensaje;}
    public LocalDate getFecha_creacion_topico() {return fecha_creacion_topico;}
    public void setFecha_creacion_topico(LocalDate fecha_creacion_topico) {this.fecha_creacion_topico = fecha_creacion_topico;}
    public Status getStatus() {return status;}
    public void setStatus(Status status) {this.status = status;}
    public Categorias getCategoria() {return categoria;}
    public void setCategoria(Categorias categoria) {this.categoria = categoria;}
    public Usuario getAutor() {return autor;}
    public void setAutor(Usuario autor) {this.autor = autor;}
    public List<Respuestas> getRespuestas() {return respuestas;}
    public void setRespuestas(List<Respuestas> respuestas) {this.respuestas = respuestas;}
    @Override
    public String toString() {
        return "Topico{" +
                "mensaje='" + mensaje + '\'' +
                ", titulo='" + titulo + '\'' +
                ", fecha_creacion_topico=" + fecha_creacion_topico +
                ", status='" + status + '\'' +
                ", autor=" + autor +
                ", respuestas=" + respuestas +
                '}';
    }
}


