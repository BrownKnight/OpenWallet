package com.openwallet.api.controllers;

import com.openwallet.api.controllers.responses.ErrorResponse;
import com.openwallet.api.data.models.listeners.UnauthorisedEntityAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {UnauthorisedEntityAccessException.class})
    public ResponseEntity<ErrorResponse> handleUnauthorisedEntityAccess(Exception exp,
                                                                        WebRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(exp.getMessage()));
    }
}
