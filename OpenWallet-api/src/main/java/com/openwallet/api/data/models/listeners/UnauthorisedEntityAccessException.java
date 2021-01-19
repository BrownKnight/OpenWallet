package com.openwallet.api.data.models.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UnauthorisedEntityAccessException extends RuntimeException {
    Logger logger = LoggerFactory.getLogger(UnauthorisedEntityAccessException.class);

    public UnauthorisedEntityAccessException(String message) {
        super(message);
        logger.error(message);
    }
}
