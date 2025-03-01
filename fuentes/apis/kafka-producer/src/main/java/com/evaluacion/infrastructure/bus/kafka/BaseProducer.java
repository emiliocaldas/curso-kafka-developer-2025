package com.evaluacion.infrastructure.bus.kafka;

import org.springframework.kafka.core.KafkaTemplate;

public abstract class BaseProducer<K, V> {

    private final KafkaTemplate<K, V> kafkaTemplate;

    protected BaseProducer(KafkaTemplate<K, V> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    protected void sendToTopic(String topic, K key, V message) {

        kafkaTemplate.send(topic, key, message);
    }
}
