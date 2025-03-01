package com.evaluacion.domain.exception;

public class RegistrarPagoException extends RuntimeException {

    public static final String MESSAGE = "Se produjo un error interno al registrar el pago";

    public RegistrarPagoException() {
        super(MESSAGE);
    }
}
