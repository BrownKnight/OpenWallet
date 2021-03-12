package com.openwallet.api.data.models.responses;

public class SuccessResponse implements SimpleResponse {
    private final boolean success;
    private String message;

    public SuccessResponse(String message) {
        this.message = message;
        this.success = true;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
