package com.openwallet.api.data.models.responses;

import java.util.List;

public interface SimpleResponse {
    boolean isSuccess();

    String getMessage();

    void setMessage(String message);

    List<SimpleResponse> getInnerResponses();

    void addInnerResponse(SimpleResponse response);
}
