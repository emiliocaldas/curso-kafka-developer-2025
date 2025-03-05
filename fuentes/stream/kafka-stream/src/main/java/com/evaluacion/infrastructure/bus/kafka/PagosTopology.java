package com.evaluacion.infrastructure.bus.kafka;

import java.math.BigDecimal;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import com.evaluacion.domain.entity.Pago;

@Component
public class PagosTopology {

    private final String inputTopic;

    public PagosTopology(@Value("${app.topic.input}") String inputTopic) {
        this.inputTopic = inputTopic;
    }

    @Autowired
    public void streams(StreamsBuilder streamsBuilder) {
        KStream<String, Pago> pagoStreams = streamsBuilder
                .stream(inputTopic, Consumed.with(Serdes.String(), new JsonSerde<>(Pago.class)))
                .selectKey((key, value) -> value.cardId());

        procesarPago(pagoStreams);
    }

    private void procesarPago(KStream<String, Pago> pagoStreams) {
        KTable<String, BigDecimal> saldoPagos = pagoStreams.map((key, pago) -> KeyValue.pair(pago.cardId(), pago))
                .groupByKey(Grouped.with(Serdes.String(), new JsonSerde<>(Pago.class)))
                .aggregate(() -> BigDecimal.ZERO,
                        (aggKey, newValue, aggValue) -> aggValue.add(newValue.accountAmount()),
                        Named.as("saldo_pagos"),
                        Materialized.as("saldo_pagos"));

        //TODO: verificar si es necesario para guardar el KTable
//        saldoPagos.toStream()
//                .to("payments-output", Produced.with(Serdes.String(), new JsonSerde<>(BigDecimal.class) ));
    }

}
