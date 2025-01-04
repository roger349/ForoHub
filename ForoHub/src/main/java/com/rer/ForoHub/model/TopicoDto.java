package com.rer.ForoHub.model;

import java.time.LocalDate;

public record TopicoDto(String tituloDto, String mensajeDto , LocalDate fechacreaciontopicoDto,String statusDto,
                        Usuario autorDto){  }

