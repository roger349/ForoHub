package com.rer.ForoHub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "topico")
@NoArgsConstructor
@AllArgsConstructor
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "mensaje")
    private String mensaje;
    @Column(name = "fecha_creacion_topico")
    private LocalDate fecha_creacion_topico;
    @Column(name = "status")
    private String status;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Cursos curso;
    @OneToMany(mappedBy = "topico")
    private List<Respuestas> respuestas;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}
    public String getMensaje() {return mensaje;}
    public void setMensaje(String mensaje) {this.mensaje = mensaje;}
    public LocalDate getFecha_creacion_topico() {return fecha_creacion_topico;}
    public void setFecha_creacion_topico(LocalDate fecha_creacion_topico) {this.fecha_creacion_topico = fecha_creacion_topico;}
    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
    public Usuario getAutor() {return autor;}
    public void setAutor(Usuario autor) {this.autor = autor;}
    public Cursos getCurso() {return curso;}
    public void setCurso(Cursos curso) {this.curso = curso;}
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
                ", curso=" + curso +
                ", respuestas=" + respuestas +
                '}';
    }
}


