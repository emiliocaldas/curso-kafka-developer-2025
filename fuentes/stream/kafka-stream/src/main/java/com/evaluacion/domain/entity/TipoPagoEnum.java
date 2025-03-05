package com.evaluacion.domain.entity;

import lombok.Getter;

@Getter
public enum TipoPagoEnum {
    ABONO("A"), CONSUMO("C");

    private final String codigo;

    TipoPagoEnum(String codigo) {
        this.codigo = codigo;
    }
}
