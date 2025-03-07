package com.evaluacion.infrastructure.bus.kafka;

import java.math.BigDecimal;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import com.evaluacion.domain.entity.Pago;
import com.evaluacion.infrastructure.util.BigDecimalDeserializer;
import com.evaluacion.infrastructure.util.BigDecimalSerializer;

@Component
public class PagosTopology {

    private final String inputTopic;
    private final String outputTopic;

    public PagosTopology(@Value("${app.topic.input}") String inputTopic,
                         @Value("${app.topic.output}") String outputTopic) {
        this.inputTopic = inputTopic;
        this.outputTopic = outputTopic;
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
                        Materialized.<String, BigDecimal, KeyValueStore<Bytes, byte[]>>as("saldo_pagos")
                                .withKeySerde(Serdes.String())
                                .withValueSerde(
                                        Serdes.serdeFrom(new BigDecimalSerializer(), new BigDecimalDeserializer())));

//        KTable<String, String> saldoPagosStr = pagoStreams.map((key, pago) -> KeyValue.pair(pago.cardId(), pago))
//                .groupByKey(Grouped.with(Serdes.String(), new JsonSerde<>(Pago.class)))
//                .aggregate(() -> "0.0",
//                        (aggKey, newValue, aggValue) -> BigDecimal.valueOf(Double.parseDouble(aggValue)).add
//                        (newValue.accountAmount()).toString(),
//                        Named.as("saldo_pagos"),
//                        Materialized.as("saldo_pagos"));

        saldoPagos.toStream()
                .to(outputTopic, Produced.with(Serdes.String(), new JsonSerde<>(BigDecimal.class)));
    }

}
