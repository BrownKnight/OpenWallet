package com.openwallet.api.data.models;

import com.openwallet.api.data.models.types.DataSource;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Id
    @GeneratedValue()
    protected Long id;
    @LastModifiedDate
    protected Date dateModified;

    @Column(nullable = true)
    protected String externalId;

    @Column(nullable = true)
    protected DataSource dataSource;

    public BaseEntity() {
        dataSource = DataSource.OpenWallet;
    }

    public BaseEntity(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
