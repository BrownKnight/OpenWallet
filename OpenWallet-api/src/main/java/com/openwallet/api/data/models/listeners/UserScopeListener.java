package com.openwallet.api.data.models.listeners;

import com.openwallet.api.data.models.UserLogin;
import com.openwallet.api.data.models.UserScopedEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class UserScopeListener {
    @PrePersist
    public void setOwningUser(UserScopedEntity entity) {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if (principal instanceof UserLogin) {
            entity.setOwnerId(((UserLogin) principal).getId());
        } else {
            throw new UnauthorisedEntityAccessException(
                    "Could not set OwnerId as principal could not be found. No Session perhaps?");
        }
    }

    @PreUpdate
    public void ensureSameOwningUser(UserScopedEntity entity) throws UnauthorisedEntityAccessException {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if (principal instanceof UserLogin) {
            Long authorisedUserId = ((UserLogin) principal).getId();
            if (entity.getOwnerId() == null || authorisedUserId.equals(entity.getOwnerId())) {
                entity.setOwnerId(authorisedUserId);
            } else {
                throw new UnauthorisedEntityAccessException(String.format(
                        "Failed to save entity due to owner id error. Current Owner: '%s'. New Owner: '%s'",
                        entity.getOwnerId(), authorisedUserId));
            }
        } else {
            throw new UnauthorisedEntityAccessException(
                    "Could not set OwnerId as principal could not be found. No Session perhaps?");
        }
    }
}
