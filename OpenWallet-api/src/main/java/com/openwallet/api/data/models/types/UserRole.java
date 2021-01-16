package com.openwallet.api.data.models.types;

import org.springframework.security.core.GrantedAuthority;

public class UserRole implements GrantedAuthority {
    public static final String ADMIN = "ADMIN";
    public static final String STANDARD = "STANDARD";

    private String authority;

    public UserRole(String authority) {
        this.authority = authority;
    }

    public UserRole() {
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
