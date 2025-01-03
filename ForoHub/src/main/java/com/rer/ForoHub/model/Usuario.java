package com.rer.ForoHub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "usuario")
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "contraseña")
    private String contraseña;
    @Column(name = "correo_electronico")
    private String correo_Electronico;
    @Column(name = "nombre_usuario")
    private String nombre_usuario;
    @Column(name="rol")
    @Enumerated(EnumType.STRING)
    private Roles rol;
    @OneToMany(mappedBy = "autor")
    private List<Topico> topicos;

    public Usuario() {
    }

    public Usuario(String contraseña, String correo_Electronico, String nombre_usuario, Roles rol) {
        this.contraseña = contraseña;
        this.correo_Electronico = correo_Electronico;
        this.nombre_usuario = nombre_usuario;
        this.rol = rol;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getContraseña() {return contraseña;}
    public void setContraseña(String contraseña) {this.contraseña = contraseña;}
    public String getCorreo_Electronico() {return correo_Electronico;}
    public void setCorreo_Electronico(String correo_Electronico) {this.correo_Electronico = correo_Electronico;}
    public String getNombre_usuario() {return nombre_usuario;}
    public void setNombre_usuario(String nombre_usuario) {this.nombre_usuario = nombre_usuario;}
    public Roles getRol() {return rol;}
    public void setRol(Roles rol) {this.rol = rol;}
    public List<Topico> getTopicos() {return topicos;}
    public void setTopicos(List<Topico> topicos) {this.topicos = topicos;}
    @Override
    public String toString() {
        return "Usuario{" +
                "contraseña='" + contraseña + '\'' +
                ", correo_Electronico='" + correo_Electronico + '\'' +
                ", nombre_usuario='" + nombre_usuario + '\'' +
                ", rol=" + rol +
                ", topicos=" + topicos +
                '}';
    }
}

