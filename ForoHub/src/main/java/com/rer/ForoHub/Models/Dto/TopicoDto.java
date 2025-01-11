package com.rer.ForoHub.Models.Dto;

import com.rer.ForoHub.Models.Enum.Categorias;

import java.sql.Date;

public record TopicoDto(String titulo, String mensaje , String fecha_creacion_topico,
                        Categorias categoria){  }

