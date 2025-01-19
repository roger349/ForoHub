package com.rer.ForoHub.Models.Enum;

import java.util.Arrays;
import java.util.List;

public enum Roles {
      ADMIN(Permisos.CREAR_USUARIO,
            Permisos.LEER_USUARIO,
            Permisos.ACTUALIZAR_USUARIO,
            Permisos.ELIMINAR_USUARIO,
            Permisos.CREAR_TOPICO,
            Permisos.LEER_TOPICO,
            Permisos.ACTUALIZAR_TOPICO,
            Permisos.ELIMINAR_TOPICO,
            Permisos.CREAR_RESPUESTAS,
            Permisos.LEER_RESPUESTAS,
            Permisos.ACTUALIZAR_RESPUESTAS,
            Permisos.ELIMINAR_RESPUESTAS),
    USUARIO(Permisos.CREAR_TOPICO,
            Permisos.LEER_TOPICO,
            Permisos.ACTUALIZAR_TOPICO,
            Permisos.ELIMINAR_TOPICO,
            Permisos.LEER_RESPUESTAS,
            Permisos.CREAR_RESPUESTAS,
            Permisos.ACTUALIZAR_RESPUESTAS,
            Permisos.ELIMINAR_RESPUESTAS);

    private final List<Permisos> permisos;
    Roles(Permisos... permisos) {
        this.permisos = Arrays.asList(permisos);
    }
    public List<Permisos> getPermisos() {
        return permisos;
    }
}


