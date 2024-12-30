package com.rer.ForoHub.security;

import com.rer.ForoHub.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class AutorizacionService {
    public boolean verificarPermiso(Usuario usuario, String permiso) {
        switch (usuario.getRol()) {
            case Admin:
                return true;
            case Usuario:
                return "Crear Tópico".equals(permiso);
            default:
                return false;
        }
    }
}


