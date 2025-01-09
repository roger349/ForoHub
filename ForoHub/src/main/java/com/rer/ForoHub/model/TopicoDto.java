package com.rer.ForoHub.model;

import java.time.LocalDate;

public record TopicoDto(String titulo, String mensaje , LocalDate fecha_creacion_topico,Status status,
                        Categorias categoria, Usuario autor){  }

