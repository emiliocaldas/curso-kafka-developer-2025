package com.evaluacion.domain.repository;

import java.util.List;

import com.evaluacion.domain.entity.Cuenta;

public interface CuentaRepository {

    List<Cuenta> consultar();
}
