package com.evaluacion.infrastructure.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.streams.StreamsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;

@Configuration
public class KafkaConfig {

    @Bean
    public KafkaStreamsConfiguration myKafkaAppIdConfiguration() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "payment-stream-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");

        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public StreamsBuilderFactoryBean myAppStreamBuilder(KafkaStreamsConfiguration kafkaStreamsConfiguration) {
        return new StreamsBuilderFactoryBean(kafkaStreamsConfiguration);
    }
}
