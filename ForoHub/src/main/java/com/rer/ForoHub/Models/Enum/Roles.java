package com.rer.ForoHub.Models.Enum;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

import java.util.Arrays;
import java.util.List;

public enum Roles {
    ADMIN(Permisos.CREAR_USUARIO, Permisos.LEER_USUARIO, Permisos.ACTUALIZAR_USUARIO, Permisos.ELIMINAR_USUARIO,
          Permisos.CREAR_TOPICO, Permisos.LEER_TOPICO, Permisos.ACTUALIZAR_TOPICO, Permisos.ELIMINAR_TOPICO,
          Permisos.CREAR_RESPUESTAS, Permisos.ACTUALIZAR_RESPUESTAS, Permisos.ELIMINAR_RESPUESTAS, Permisos.LEER_RESPUESTAS,
          Permisos.CREAR_CURSOS, Permisos.ACTUALIZAR_CURSOS, Permisos.LEER_CURSOS, Permisos.ELIMINAR_CURSOS),
    USUARIO(Permisos.CREAR_TOPICO, Permisos.LEER_RESPUESTAS, Permisos.ACTUALIZAR_RESPUESTAS, Permisos.LEER_CURSOS);

    private final List<Permisos> permisos;

    Roles(Permisos... permisos) {
        this.permisos = Arrays.asList(permisos);
    }

    public List<Permisos> getPermisos() {
        return permisos;
    }
}


