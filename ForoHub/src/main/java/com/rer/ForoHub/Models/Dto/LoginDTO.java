package com.rer.ForoHub.Models.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO (

        @NotBlank(message = "El nombre de usuario no debe estar vacío")
        String username,
        @NotBlank(message = "La contraseña no debe estar vacío")
        @Size(min = 6, message = "La contraseña debe tener 6 o mas caracteres")
        String password) {
}


