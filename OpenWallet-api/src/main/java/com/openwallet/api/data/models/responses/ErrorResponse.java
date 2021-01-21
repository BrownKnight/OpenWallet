package com.openwallet.api.data.models.responses;

public class ErrorResponse {
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
