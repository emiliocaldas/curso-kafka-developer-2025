package com.evaluacion.infrastructure.controller.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evaluacion.application.CuentaService;
import com.evaluacion.infrastructure.dto.response.ConsultarCuentaResponse;

@RestController
@RequestMapping("/api/v1")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<ConsultarCuentaResponse>> consultar() {

        List<ConsultarCuentaResponse> cuentas = cuentaService.consultar()
                .stream()
                .map(ConsultarCuentaResponse::fromCuentaDomain)
                .collect(Collectors.toList());

        return ResponseEntity.ok(cuentas);
    }
}
