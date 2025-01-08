package com.rer.ForoHub.model;

public record UsuarioDTO(Long idDTO, String contraseñaDto,String nombreUsuarioDto ,
                         String correoElectronicoDto, Roles rolDto) {
}
