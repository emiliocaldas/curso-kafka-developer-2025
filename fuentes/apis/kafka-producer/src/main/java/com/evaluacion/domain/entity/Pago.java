package com.evaluacion.domain.entity;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record Pago(long timestamp, String cardId, BigDecimal amount, String type) {

    public String toBusMessage() {

        return "%s|%s|%.2f|%s".formatted(timestamp, cardId, amount, type);
    }
}
