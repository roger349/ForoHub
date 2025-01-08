package com.rer.ForoHub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "respuestas")
@NoArgsConstructor
@AllArgsConstructor
public class Respuestas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "mensaje_respuestas")
    private String mensaje_respuestas;
    @Column(name = "fecha_creacion_respuestas")
    private LocalDate fecha_creacion_respuestas;
    @Column(name = "estado")
    private Boolean estado;
    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getMensaje_respuestas() {return mensaje_respuestas;}
    public void setMensaje_respuestas(String mensaje_respuestas) {this.mensaje_respuestas = mensaje_respuestas;}
    public LocalDate getFecha_creacion_respuestas() {return fecha_creacion_respuestas;}
    public void setFecha_creacion_respuestas(LocalDate fecha_creacion_respuestas) {this.fecha_creacion_respuestas = fecha_creacion_respuestas;}
    public Boolean getEstado() {return estado;}
    public void setEstado(Boolean estado) {this.estado = estado;}
    public Topico getTopico() {return topico;}
    public void setTopico(Topico topico) {this.topico = topico;}
    @Override
    public String toString() {
        return "Respuestas{" +
                "mensaje_respuestas='" + mensaje_respuestas + '\'' +
                ", fecha_creacion_respuestas=" + fecha_creacion_respuestas +
                ", estado=" + estado +
                ", topico=" + topico +
                '}';
    }
}
