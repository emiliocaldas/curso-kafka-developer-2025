package com.evaluacion.infrastructure.dto.response;

import java.math.BigDecimal;

import com.evaluacion.domain.entity.Cuenta;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ConsultarCuentaResponse(@JsonProperty("card_id") String cardId,
                                      BigDecimal total) {

    public static ConsultarCuentaResponse fromCuentaDomain(Cuenta cuenta) {

        return ConsultarCuentaResponse.builder()
                .cardId(cuenta.cardId())
                .total(cuenta.totalAmount())
                .build();
    }

}
