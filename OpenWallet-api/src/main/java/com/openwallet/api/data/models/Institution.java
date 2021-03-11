package com.openwallet.api.data.models;

import javax.persistence.Entity;

@Entity
public class Institution extends BaseEntity {
    private String name;

    public Institution(String name) {
        this.name = name;
    }

    public Institution(String name, String externalId) {
        this.name = name;
        this.externalId = externalId;
    }

    public Institution() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
