package com.rer.ForoHub.Models.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "respuestas")
@AllArgsConstructor
public class Respuestas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //@NotBlank(message = "No debe estar vacia")
    @Column(name = "mensaje_respuestas")
    private String mensaje_respuestas;
    //@NotBlank
    //@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "La fecha debe estar en el formato yyyy-MM-dd")
    @Column(name = "fecha_creacion_respuestas")
    private String fecha_creacion_respuestas;
    //@NotNull(message = "El estado no puede ser nulo, tiene que ser true o false")
    @Column(name = "estado")
    private Boolean estado;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "topico_id")
    @JsonManagedReference
    private Topico topico;

    public Respuestas(){ }

    public Respuestas(String mensaje_respuestas, String fecha_creacion_respuestas, Boolean estado, Topico topico) {
        this.mensaje_respuestas = mensaje_respuestas;
        this.fecha_creacion_respuestas = fecha_creacion_respuestas;
        this.estado = estado;
        this.topico = topico;
    }

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}
    public String getMensaje_respuestas() {return mensaje_respuestas;}
    public void setMensaje_respuestas(String mensaje_respuestas) {this.mensaje_respuestas = mensaje_respuestas;}
    public String getFecha_creacion_respuestas() {return fecha_creacion_respuestas;}
    public void setFecha_creacion_respuestas(String fecha_creacion_respuestas) {this.fecha_creacion_respuestas = fecha_creacion_respuestas;}
    public Boolean getEstado() {return estado;}
    public void setEstado(Boolean estado) {this.estado = estado;}
    public Topico getTopico() {return topico;}
    public void setTopico(Topico topico) {this.topico = topico;}
}
