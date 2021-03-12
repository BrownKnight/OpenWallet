package com.openwallet.api.data.models;

import com.openwallet.api.data.models.types.DataSource;

import javax.persistence.Entity;

@Entity
public class Institution extends BaseEntity {
    private String name;

    public Institution(String name) {
        super();
        this.name = name;
    }

    public Institution(String name, String externalId, DataSource dataSource) {
        super(dataSource);
        this.name = name;
        this.externalId = externalId;
    }

    public Institution() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
