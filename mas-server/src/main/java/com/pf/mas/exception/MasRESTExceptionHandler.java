package com.pf.mas.exception;

import com.pf.common.exception.MasRuntimeException;
import com.pf.common.exception.MasThirdPartyApiException;
import com.pf.corpository.exception.MasRequestException;
import com.pf.karza.exception.MasKarzaITRInvalidRequestException;
import com.pf.mas.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class MasRESTExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({MasReportSheetReaderException.class, MasJSONException.class})
    public ResponseEntity<ErrorResponse> handleMasRESTException(Exception ex) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MasRuntimeException.class)
    public ResponseEntity<ErrorResponse> handleMasRuntimeException(MasRuntimeException ex) {
        return buildErrorResponse(ex, ex.getStatus());
    }

    @ExceptionHandler({
            MasRequestException.class,
            MasGSTInvalidRequestParametersException.class,
            MasGetGST3BReportException.class,
            MasKarzaITRInvalidRequestException.class
    })
    public ResponseEntity<ErrorResponse> handleMasRequestException(Exception ex) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MasGSTNoEntityFoundException.class)
    public ResponseEntity<ErrorResponse> handleMasNotFoundException(MasGSTNoEntityFoundException ex) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MasThirdPartyApiException.class)
    public ResponseEntity<ErrorResponse> handleMasThirdPartyApiException(MasThirdPartyApiException ex) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .message(ex.getMessage())
                .progressStatus(ex.getProgressStatus().getDescription())
                .errorCode(ex.getStatus().value())
                .build(), ex.getStatus());
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, HttpStatus httpStatus) {
        return new ResponseEntity<>(ErrorResponse.builder().message(ex.getMessage()).errorCode(httpStatus.value()).build(), httpStatus);
    }
}
