package com.openwallet.api.data.models.responses;

import java.util.ArrayList;
import java.util.List;

public class SuccessResponse implements SimpleResponse {
    private final boolean success;
    private final List<SimpleResponse> innerResponses;
    private String message;

    public SuccessResponse(String message) {
        this.message = message;
        this.success = true;
        this.innerResponses = new ArrayList<>();
    }

    public SuccessResponse(String message, List<SimpleResponse> innerResponses) {
        this.message = message;
        this.success = true;
        this.innerResponses = innerResponses;
    }

    public List<SimpleResponse> getInnerResponses() {
        return innerResponses;
    }

    public void addInnerResponse(SimpleResponse innerResponse) {
        this.innerResponses.add(innerResponse);
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
