package com.openwallet.api.data.models.responses;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse implements SimpleResponse {
    private final List<SimpleResponse> innerResponses;
    private boolean success;
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
        this.success = false;
        this.innerResponses = new ArrayList<>();
    }

    public List<SimpleResponse> getInnerResponses() {
        return innerResponses;
    }

    public void addInnerResponse(SimpleResponse innerResponse) {
        if (!innerResponse.isSuccess()) {
            this.success = false;
            this.message = "Error occurred in an inner request";
        }
        this.innerResponses.add(innerResponse);
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
