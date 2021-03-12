package com.openwallet.api.data.models.responses;

public interface SimpleResponse {
    boolean isSuccess();

    String getMessage();

    void setMessage(String message);
}
