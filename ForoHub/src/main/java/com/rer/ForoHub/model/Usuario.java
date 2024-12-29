package com.rer.ForoHub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "correo_electronico")
    private String correo_Electronico;
    @Column(name = "contraseña")
    private String contraseña;
    @Column(name="rol")
    @Enumerated(EnumType.STRING)
    private Roles rol;
    @OneToMany(mappedBy = "autor")
    private List<Topico> topicos;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public String getCorreo_Electronico() {return correo_Electronico;}
    public void setCorreo_Electronico(String correo_Electronico) {this.correo_Electronico = correo_Electronico;}
    public String getContraseña() {return contraseña;}
    public void setContraseña(String contraseña) {this.contraseña = contraseña;}
    public Roles getRol() {return rol;}
    public void setRol(Roles rol) {this.rol = rol;}
    public List<Topico> getTopicos() {return topicos;}
    public void setTopicos(List<Topico> topicos) {this.topicos = topicos;}
    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", correo_Electronico='" + correo_Electronico + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", rol=" + rol +
                ", topicos=" + topicos +
                '}';
    }
}

