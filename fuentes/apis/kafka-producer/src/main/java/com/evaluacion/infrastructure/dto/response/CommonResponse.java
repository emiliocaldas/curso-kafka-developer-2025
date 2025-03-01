package com.evaluacion.infrastructure.dto.response;

import lombok.Builder;

@Builder
public record CommonResponse(String status, String message) {

    public static final String STATUS_OK = "OK";
    public static final String STATUS_ERROR = "ERROR";
    public static final String STATUS_CLIENT_ERROR = "CLIENT_ERROR";
    public static final String MESSAGE_OK = "Pago registrado correctamente.";

    public static CommonResponse buildOkResponse() {

        return CommonResponse.builder()
                .status(STATUS_OK)
                .message(MESSAGE_OK)
                .build();
    }

    public static CommonResponse buildErrorResponse(Exception exception) {

        return CommonResponse.builder()
                .status(STATUS_ERROR)
                .message(exception.getMessage())
                .build();
    }

    public static CommonResponse buildClientErrorResponse(String message) {

        return CommonResponse.builder()
                .status(STATUS_CLIENT_ERROR)
                .message(message)
                .build();
    }
}
