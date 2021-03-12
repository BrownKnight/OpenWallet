package com.openwallet.api.data.models.responses;

public class ErrorResponse implements SimpleResponse {
    private final boolean success;
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
        this.success = false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }
}
