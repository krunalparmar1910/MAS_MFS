package com.pf.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class MasRuntimeException extends RuntimeException {
    @Getter
    private final HttpStatus status;

    public MasRuntimeException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public MasRuntimeException(Throwable cause) {
        super(cause);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
