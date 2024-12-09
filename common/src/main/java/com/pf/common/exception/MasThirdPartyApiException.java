package com.pf.common.exception;

import com.pf.common.enums.ProgressStatus;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MasThirdPartyApiException extends Exception {
    private final HttpStatus status;

    private final ProgressStatus progressStatus;

    public MasThirdPartyApiException(HttpStatus status, String message, ProgressStatus progressStatus) {
        super(message);
        this.status = status;
        this.progressStatus = progressStatus;
    }
}
