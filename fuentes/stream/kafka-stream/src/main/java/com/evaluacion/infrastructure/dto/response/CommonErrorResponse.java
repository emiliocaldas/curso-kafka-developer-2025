package com.evaluacion.infrastructure.dto.response;

import lombok.Builder;

@Builder
public record CommonErrorResponse(String status, String message) {

    public static final String STATUS_ERROR = "ERROR";
    public static final String MESSAGE_ERROR = "Se produjo un error interno.";

    public static CommonErrorResponse buildErrorResponse() {

        return CommonErrorResponse.builder()
                .status(STATUS_ERROR)
                .message(MESSAGE_ERROR)
                .build();
    }
}
