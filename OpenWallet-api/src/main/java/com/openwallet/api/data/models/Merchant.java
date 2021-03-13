package com.openwallet.api.data.models;

import com.openwallet.api.data.models.types.DataSource;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Merchant extends BaseEntity {
    @Column(unique = true, nullable = false)
    @Getter
    @Setter
    private Integer isoCategoryCode;

    @Column(nullable = false)
    @Getter
    @Setter
    private String merchantName;

    public Merchant(Integer isoCategoryCode, String merchantName) {
        super(DataSource.OpenWallet);
        this.isoCategoryCode = isoCategoryCode;
        this.merchantName = merchantName;
    }
}
