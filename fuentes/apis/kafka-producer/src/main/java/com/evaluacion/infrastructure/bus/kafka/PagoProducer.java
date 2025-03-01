package com.evaluacion.infrastructure.bus.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

import com.evaluacion.domain.entity.Pago;
import com.evaluacion.domain.repository.PagoRepository;

@Repository
public class PagoProducer extends BaseProducer<String, String> implements PagoRepository {

    private final String topic;

    public PagoProducer(KafkaTemplate<String, String> kafkaTemplate,
                        @Value("${app.topic.pagos}") String topic) {
        super(kafkaTemplate);
        this.topic = topic;
    }

    @Override
    public void guardarPago(Pago pago) {

        sendToTopic(topic, null, pago.toBusMessage());
    }

}
