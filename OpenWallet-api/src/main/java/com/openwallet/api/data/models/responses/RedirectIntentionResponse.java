package com.openwallet.api.data.models.responses;

public class RedirectIntentionResponse extends SuccessResponse {
    private String redirectUrl;

    public RedirectIntentionResponse(String message, String redirectUrl) {
        super(message);
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
