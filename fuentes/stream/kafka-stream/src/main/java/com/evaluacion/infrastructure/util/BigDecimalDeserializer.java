package com.evaluacion.infrastructure.util;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class BigDecimalDeserializer implements Deserializer<BigDecimal> {

    @Override
    public BigDecimal deserialize(String topic, byte[] data) {

        if (data == null) {
            return null;
        }

        String stringData = new String(data, StandardCharsets.UTF_8);

        try {
            return new BigDecimal(stringData);
        } catch (RuntimeException e) {
            throw new SerializationException("Error al deserializar data", e);
        }
    }
}
