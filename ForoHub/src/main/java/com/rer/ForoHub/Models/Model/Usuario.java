package com.rer.ForoHub.Models.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rer.ForoHub.Models.Enum.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "usuario")
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //@NotBlank(message = "La contraseña no debe estar vacío")
    //@Size(min = 6, message = "La contraseña debe tener 6 o mas caracteres")
    @Column(name = "contraseña")
    private String contraseña;
    //@NotBlank(message = "El nombre de usuario no debe estar vacío")
    @Column(name = "nombre_usuario")
    private String nombre_usuario;
    //@NotBlank(message = "El correo electronico no debe estar vacío")
    //@Email(message = "El correo electronico debe ser válido")
    @Column(name = "correo_electronico")
    private String correo_Electronico;
    //@NotNull(message = "El rol no debe estar vacío, debe contener: USUARIO o ADMIN")
    @Column(name="rol")
    @Enumerated(EnumType.STRING)
    private Roles rol;
    @JsonIgnore
    @OneToMany(mappedBy = "autor")
    @JsonBackReference
    private List<Topico> topicos;

    public Usuario() { }

    public Usuario(String contraseña, String nombre_usuario, String correo_Electronico, Roles rol) {
        this.contraseña = contraseña;
        this.nombre_usuario = nombre_usuario;
        this.correo_Electronico = correo_Electronico;
        this.rol = rol;
    }

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}
    public String getContraseña() {return contraseña;}
    public void setContraseña(String contraseña) {this.contraseña = contraseña;}
    public String getNombre_usuario() {return nombre_usuario;}
    public void setNombre_usuario(String nombre_usuario) {this.nombre_usuario = nombre_usuario;}
    public String getCorreo_Electronico() {return correo_Electronico;}
    public void setCorreo_Electronico(String correo_Electronico) {this.correo_Electronico = correo_Electronico;}
    public Roles getRol() {return rol;}
    public void setRol(Roles rol) {this.rol = rol;}
    public List<Topico> getTopicos() {return topicos;}
    public void setTopicos(List<Topico> topicos) {this.topicos = topicos;}
}

