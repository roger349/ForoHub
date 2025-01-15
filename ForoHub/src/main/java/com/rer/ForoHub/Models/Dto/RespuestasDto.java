package com.rer.ForoHub.Models.Dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RespuestasDto(
        @NotBlank(message = "No debe estar vacia")
        String mensajeRespuesta,
        @NotBlank
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "La fecha debe estar en el formato yyyy-MM-dd")
        String fechacreacionrespuesta,
        @NotNull(message = "El estado no puede ser nulo, tiene que ser true o false")
        Boolean estadoRespuesta) {
}
