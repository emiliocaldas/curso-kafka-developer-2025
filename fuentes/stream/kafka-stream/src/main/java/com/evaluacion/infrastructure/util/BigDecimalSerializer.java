package com.evaluacion.infrastructure.util;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

import org.apache.kafka.common.serialization.Serializer;

public class BigDecimalSerializer implements Serializer<BigDecimal> {

    @Override
    public byte[] serialize(String topic, BigDecimal data) {

        if (data == null) {
            return null;
        }

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        return decimalFormat.format(data)
                .getBytes(StandardCharsets.UTF_8);
    }
}
