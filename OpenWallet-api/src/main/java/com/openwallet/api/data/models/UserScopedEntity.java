package com.openwallet.api.data.models;

import com.openwallet.api.data.models.listeners.UserScopeListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(UserScopeListener.class)
public abstract class UserScopedEntity extends BaseEntity {
    protected Long ownerId;

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public UserScopedEntity() {
    }
}
