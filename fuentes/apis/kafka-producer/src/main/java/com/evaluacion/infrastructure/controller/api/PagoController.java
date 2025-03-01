package com.evaluacion.infrastructure.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evaluacion.application.PagoService;
import com.evaluacion.domain.entity.TipoPagoEnum;
import com.evaluacion.domain.exception.RegistrarPagoException;
import com.evaluacion.infrastructure.dto.request.PagoRequest;
import com.evaluacion.infrastructure.dto.response.CommonResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping("/payments")
    public ResponseEntity<CommonResponse> guardarPago(@RequestBody @Valid PagoRequest pagoRequest) {

        if (!TipoPagoEnum.esCodigoValido(pagoRequest.type())) {

            return ResponseEntity.badRequest()
                    .body(CommonResponse.buildClientErrorResponse("Parámetro 'type' debe ser 'A' o 'C'."));
        }

        try {
            pagoService.guardarPago(pagoRequest.toPagoDomain());

            return ResponseEntity.ok(CommonResponse.buildOkResponse());
        } catch (RegistrarPagoException e) {

            return ResponseEntity.internalServerError()
                    .body(CommonResponse.buildErrorResponse(e));
        }
    }

}
