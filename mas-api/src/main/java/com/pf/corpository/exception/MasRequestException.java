package com.pf.corpository.exception;

public class MasRequestException extends Exception {
    public MasRequestException(String url, int code, String message) {
        super(String.format("%s HTTP %d: %s", url, code, message));
    }

    public MasRequestException(String url, int code) {
        super(String.format("%s HTTP %d", url, code));
    }

    public MasRequestException(Throwable cause) {
        super(cause);
    }
}
