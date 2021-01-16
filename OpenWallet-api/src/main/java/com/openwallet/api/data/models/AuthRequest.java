package com.openwallet.api.data.models;

public class AuthRequest {
    private String username;
    private String password;

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected AuthRequest() {
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
