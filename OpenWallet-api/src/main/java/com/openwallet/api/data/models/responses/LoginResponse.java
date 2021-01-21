package com.openwallet.api.data.models.responses;

public class LoginResponse {
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    protected LoginResponse() {}

    public String getToken() {
        return token;
    }
}
