package com.openwallet.api.controllers.exception.handlers;

import com.openwallet.api.data.models.listeners.UnauthorisedEntityAccessException;
import com.openwallet.api.data.models.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import yapily.ApiException;

@ControllerAdvice
public class ErrorResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {UnauthorisedEntityAccessException.class})
    public ResponseEntity<ErrorResponse> handleUnauthorisedEntityAccess(Exception exp, WebRequest request) {
        return standardErrorForStatus(HttpStatus.FORBIDDEN, exp);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception exp, WebRequest request) {
        return standardErrorForStatus(HttpStatus.BAD_REQUEST, exp);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<ErrorResponse> handleBadCredentials(Exception exp, WebRequest request) {
        logger.error(exp.getMessage());
        return standardErrorForStatus(HttpStatus.UNAUTHORIZED, exp);
    }

    @ExceptionHandler(value = {ApiException.class})
    public ResponseEntity<ErrorResponse> handleYapilyException(Exception exp, WebRequest request) {
        logger.error("YAPILY Exception: " + exp.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Error occurred in a call to the external service Yapily"));
    }

    private ResponseEntity<ErrorResponse> standardErrorForStatus(HttpStatus status, Exception exp) {
        return ResponseEntity.status(status)
                .body(new ErrorResponse(exp.getMessage()));
    }
}
