package com.rer.ForoHub.Models.Dto;

import com.rer.ForoHub.Models.Enum.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioDTO(

        @NotBlank(message = "La contraseña no debe estar vacío")
        @Size(min = 6, message = "La contraseña debe tener 6 o mas caracteres")
        String contraseñaDto,
        @NotBlank(message = "El nombre de usuario no debe estar vacío")
        String nombreUsuarioDto,
        @NotBlank(message = "El correo electronico no debe estar vacío")
        @Email(message = "El correo electronico debe ser válido")
        String correoElectronicoDto,
        @NotNull(message = "El rol no debe estar vacío, debe contener: USUARIO o ADMIN")
        Roles rolDto) {

        public UsuarioDTO(String contraseñaDto, String nombreUsuarioDto, String correoElectronicoDto, Roles rolDto) {

        this.contraseñaDto = contraseñaDto;
        this.nombreUsuarioDto = nombreUsuarioDto;
        this.correoElectronicoDto = correoElectronicoDto;
        this.rolDto = rolDto;

        }
}
