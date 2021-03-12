package com.openwallet.api.util;

import com.openwallet.api.data.models.UserLogin;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityHelper {
    public static Long getCurrentUserId() {
        return ((UserLogin) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()).getId();
    }
}
