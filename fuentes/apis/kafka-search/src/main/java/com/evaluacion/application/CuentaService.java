package com.evaluacion.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.evaluacion.domain.entity.Cuenta;
import com.evaluacion.domain.repository.CuentaRepository;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public List<Cuenta> consultar() {

        return cuentaRepository.consultar();
    }

}
