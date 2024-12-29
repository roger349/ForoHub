package com.rer.ForoHub.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity(name = "cursos")
@Table(name = "cursos")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cursos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id ;
    @Column(name="nombre_curso")
    private String nombre_curso;
    @Column(name="categoria")
    @Enumerated(EnumType.STRING)
    private Categorias categoria;
    @OneToMany(mappedBy = "curso")
    private List<Topico> topico_cursos;


    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getNombre_curso() {return nombre_curso;}
    public void setNombre_curso(String nombre_curso) {this.nombre_curso = nombre_curso;}
    public Categorias getCategoria() {return categoria;}
    public void setCategoria(Categorias categoria) {this.categoria = categoria;}
    public List<Topico> getTopico_cursos() {return topico_cursos;}
    public void setTopico_cursos(List<Topico> topico_cursos) {this.topico_cursos = topico_cursos;}
    @Override
    public String toString() {
        return "Cursos{" +
                "nombre_curso='" + nombre_curso + '\'' +
                ", categoria=" + categoria +
                ", cursos=" + topico_cursos +
                '}';
    }
}
