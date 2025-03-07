package com.evaluacion.infrastructure.bus.kafka;

import java.math.BigDecimal;
import java.util.List;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Repository;

import com.evaluacion.domain.entity.Cuenta;
import com.evaluacion.domain.repository.CuentaRepository;

@Repository
public class CuentaKTableRepository implements CuentaRepository {

    private final StreamsBuilderFactoryBean streamsBuilderFactoryBean;

    public CuentaKTableRepository(StreamsBuilderFactoryBean streamsBuilderFactoryBean) {
        this.streamsBuilderFactoryBean = streamsBuilderFactoryBean;
    }

    @Override
    public List<Cuenta> consultar() {

        ReadOnlyKeyValueStore<String, BigDecimal> saldoPagosStoreData = streamsBuilderFactoryBean.getKafkaStreams()
                .store(StoreQueryParameters.fromNameAndType("saldo_pagos", QueryableStoreTypes.keyValueStore()));

        var saldoPagos = saldoPagosStoreData.all();
        var spliterator = Spliterators.spliteratorUnknownSize(saldoPagos, 0);

        return StreamSupport.stream(spliterator, false)
                .map(data -> new Cuenta(data.key, data.value))
                .collect(Collectors.toList());
    }
}
