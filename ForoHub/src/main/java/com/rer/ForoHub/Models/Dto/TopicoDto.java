package com.rer.ForoHub.Models.Dto;

import com.rer.ForoHub.Models.Enum.Categorias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TopicoDto(

        @NotBlank(message = "El titulo no debe estar vacio")
        String titulo,
        @NotBlank(message = "El contenido no debe estar vacio")
        String mensaje,
        @NotBlank
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "La fecha debe estar en el formato yyyy-MM-dd")
        String fecha_creacion_topico,
        @NotNull(message ="Las Categorias deben ser: Programacion, BackEnd, FrontEnd, DataScience, DevOps" )
        Categorias categoria)  {
}

