package com.rer.ForoHub.repository;

import com.rer.ForoHub.model.Roles;
import com.rer.ForoHub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, java.lang.Long> {

    @Query("select u from Usuario u where u.nombre_usuario = :nombre_usuario")
    Usuario findByNombre_usuario(String nombre_usuario);
    @Query("select count(u) from Usuario u where u.rol = :roles")
    long countByRol(Roles roles);
}

