package com.openwallet.api.data.models.responses;

public class RedirectIntentionResponse implements SimpleResponse {
    private boolean success;
    private String message;

    private String redirectUrl;

    public RedirectIntentionResponse(String message, String redirectUrl) {
        this.success = true;
        this.message = message;
        this.redirectUrl = redirectUrl;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
