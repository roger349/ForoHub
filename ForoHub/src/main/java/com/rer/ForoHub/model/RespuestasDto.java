package com.rer.ForoHub.model;

import java.time.LocalDate;

public record RespuestasDto(String mensajeRespuesta, LocalDate fechacreacionrespuestas, Boolean estadoRespuesta) { }
