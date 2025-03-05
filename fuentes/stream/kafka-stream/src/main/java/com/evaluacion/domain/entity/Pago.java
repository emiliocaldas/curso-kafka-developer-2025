package com.evaluacion.domain.entity;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record Pago(long timestamp, String cardId, BigDecimal amount, String type) {

    public BigDecimal accountAmount() {
        return amount.multiply(TipoPagoEnum.ABONO.getCodigo().equals(type)
                ? BigDecimal.ONE : BigDecimal.valueOf(-1));
    }
}
