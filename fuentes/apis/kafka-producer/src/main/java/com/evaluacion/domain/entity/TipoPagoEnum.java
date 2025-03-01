package com.evaluacion.domain.entity;

import java.util.stream.Stream;

import lombok.Getter;

@Getter
public enum TipoPagoEnum {
    ABONO("A"), CONSUMO("C");

    private final String codigo;

    TipoPagoEnum(String codigo) {
        this.codigo = codigo;
    }

    public static boolean esCodigoValido(String codigo) {

        return Stream.of(TipoPagoEnum.values())
                .anyMatch(tip -> tip.getCodigo().equals(codigo));
    }

}
