package com.evaluacion.infrastructure.dto.request;

import java.math.BigDecimal;

import com.evaluacion.domain.entity.Pago;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PagoRequest(@Positive long timestamp,
                          @JsonProperty("card_id") @NotBlank String cardId,
                          @DecimalMin(value = "0.0", inclusive = false) BigDecimal amount,
                          @Size(min = 1, max = 1) String type) {

    public Pago toPagoDomain() {

        return Pago.builder()
                .timestamp(timestamp)
                .cardId(cardId)
                .amount(amount)
                .type(type)
                .build();
    }
}
