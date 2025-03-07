package com.evaluacion.infrastructure.kafka;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppTopology {

    private final String inputTopic;

    public AppTopology(@Value("${app.topic.input}") String inputTopic) {
        this.inputTopic = inputTopic;
    }

    @Autowired
    public void streams(StreamsBuilder streamsBuilder) {
        KStream<String, String> stream = streamsBuilder
                .stream(inputTopic, Consumed.with(Serdes.String(), Serdes.String()));

        //no se hace ninguna accion
        stream.foreach((key, value) -> {
        });
    }


}
