package com.evaluacion.application;

import org.springframework.stereotype.Service;

import com.evaluacion.domain.entity.Pago;
import com.evaluacion.domain.exception.RegistrarPagoException;
import com.evaluacion.domain.repository.PagoRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PagoService {

    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    public void guardarPago(Pago pago) {

        log.info("Iniciando registro de pago: {}", pago.toString());

        try {
            pagoRepository.guardarPago(pago);

            log.info("Pago registrado correctamente");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RegistrarPagoException();
        }
    }

}
