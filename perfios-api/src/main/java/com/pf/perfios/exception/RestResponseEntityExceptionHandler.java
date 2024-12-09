package com.pf.perfios.exception;

import com.pf.common.response.CommonResponse;
import com.pf.perfios.utils.PerfiosHeaderUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.regex.Pattern;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<CommonResponse> handlerHttpClientErrorException(
            HttpClientErrorException ex, WebRequest request) {
        String message = PerfiosHeaderUtil.getErrorMessage(ex.getResponseBodyAsString());

        return new ResponseEntity<>(
                new CommonResponse(message, null), ex.getStatusCode());
    }

    @ExceptionHandler({RestClientException.class})
    public ResponseEntity<CommonResponse> handleRestClientException(
            RestClientException ex, WebRequest request) {
        return new ResponseEntity<>(
                new CommonResponse(ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MasEntityNotFoundException.class})
    public ResponseEntity<CommonResponse> handleEntityNotFoundException(
            MasEntityNotFoundException ex) {
        return new ResponseEntity<>(
                new CommonResponse(ex.getMessage(), null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MasBadRequestException.class})
    public ResponseEntity<CommonResponse> handleBadRequestException(
            MasBadRequestException ex) {
        return new ResponseEntity<>(
                new CommonResponse(ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
    @Nullable
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        StringBuilder errorMessages = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorMessages.append(fieldName + " " + errorMessage + ", ");
        });

        return new ResponseEntity<>(
                new CommonResponse(errorMessages.toString(), null), HttpStatus.BAD_REQUEST);
    }

    private static final Pattern ENUM_MSG = Pattern.compile("values accepted for Enum class: \\[([^\\]])\\]");

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(
                new CommonResponse(ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MasPerfiosException.class})
    public ResponseEntity<CommonResponse> handlePerfiosException(
            MasBadRequestException ex) {
        return new ResponseEntity<>(
                new CommonResponse(ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}