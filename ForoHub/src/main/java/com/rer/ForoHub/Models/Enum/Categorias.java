package com.rer.ForoHub.Models.Enum;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum Categorias {
    Programacion,
    BackEnd,
    FrontEnd,
    DataScience,
    DevOps,
    @JsonEnumDefaultValue
    Desconocido
}

