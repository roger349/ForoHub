package com.rer.ForoHub.model;

public record UsuarioDTO(Long idDTO, String contraseñaDto,String nombreUsuarioDto ,
                         String correoElectronicoDto, Roles rolDto) {
    public UsuarioDTO(Long idDTO, String contraseñaDto, String nombreUsuarioDto, String correoElectronicoDto, Roles rolDto) {
        this.idDTO = idDTO;
        this.contraseñaDto = contraseñaDto;
        this.nombreUsuarioDto = nombreUsuarioDto;
        this.correoElectronicoDto = correoElectronicoDto;
        this.rolDto = rolDto;
    }
}
