package com.rer.ForoHub.Models.Dto;

import com.rer.ForoHub.Models.Enum.Roles;
import com.rer.ForoHub.Models.Model.Usuario;

public record UsuarioDTO(Usuario idDTO, String contraseñaDto, String nombreUsuarioDto ,
                         String correoElectronicoDto, Roles rolDto) {
    public UsuarioDTO(Usuario idDTO, String contraseñaDto, String nombreUsuarioDto, String correoElectronicoDto, Roles rolDto) {
        this.idDTO = idDTO;
        this.contraseñaDto = contraseñaDto;
        this.nombreUsuarioDto = nombreUsuarioDto;
        this.correoElectronicoDto = correoElectronicoDto;
        this.rolDto = rolDto;
    }
}
