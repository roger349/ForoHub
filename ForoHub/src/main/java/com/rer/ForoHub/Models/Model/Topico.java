package com.rer.ForoHub.Models.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rer.ForoHub.Models.Enum.Categorias;
import com.rer.ForoHub.Models.Enum.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "topico")
@AllArgsConstructor
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "El titulo no debe estar vacio")
    @Column(name = "titulo")
    private String titulo;
    @NotBlank(message = "El contenido no debe estar vacio")
    @Column(name = "mensaje")
    private String mensaje;
    @NotBlank
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "La fecha debe estar en el formato yyyy-MM-dd")
    @Column(name = "fecha_creacion_topico")
    private String fecha_creacion_topico;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @NotNull(message ="Programacion, BackEnd, FrontEnd, DataScience, DevOps" )
    @Column(name="categoria")
    @Enumerated(EnumType.STRING)
    private Categorias categoria;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    @JsonManagedReference
    private Usuario autor;
    @OneToMany(mappedBy = "topico")
    @JsonBackReference
    private List<Respuestas> respuestas;

    public Topico(){
    }
    public Topico(String titulo, String mensaje, String fecha_creacion_topico,
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
    public String getFecha_creacion_topico() {return fecha_creacion_topico;}
    public void setFecha_creacion_topico(String fecha_creacion_topico) {this.fecha_creacion_topico = fecha_creacion_topico;}
    public Status getStatus() {return status;}
    public void setStatus(Status status) {this.status = status;}
    public Categorias getCategoria() {return categoria;}
    public void setCategoria(Categorias categoria) {this.categoria = categoria;}
    public Usuario getAutor() {return autor;}
    public void setAutor(Usuario autor) {this.autor = autor;}
    public List<Respuestas> getRespuestas() {return respuestas;}
    public void setRespuestas(List<Respuestas> respuestas) {this.respuestas = respuestas;}
}


