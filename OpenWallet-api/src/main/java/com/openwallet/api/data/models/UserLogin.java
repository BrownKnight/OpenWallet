package com.openwallet.api.data.models;

import javax.persistence.Entity;

@Entity
public class UserLogin extends BaseEntity {
    private String username;
    private String password;

    protected UserLogin() {}

    public UserLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

