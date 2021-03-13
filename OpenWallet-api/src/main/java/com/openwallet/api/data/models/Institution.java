package com.openwallet.api.data.models;

import com.openwallet.api.data.models.types.DataSource;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Institution extends BaseEntity {
    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String name;

    @Getter
    @Setter
    @Column(nullable = true)
    private String iconUrl;

    @Getter
    @Setter
    @Column(nullable = true)
    private String logoUrl;

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
}
