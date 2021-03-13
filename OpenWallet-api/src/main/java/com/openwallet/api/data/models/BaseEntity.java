package com.openwallet.api.data.models;

import com.openwallet.api.data.models.types.DataSource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Id
    @GeneratedValue()
    @Getter
    @Setter
    protected Long id;

    @LastModifiedDate
    @Getter
    protected Date dateModified;

    @Column(nullable = true)
    @Getter
    @Setter
    protected String externalId;

    @Column(nullable = true)
    @Getter
    @Setter
    protected DataSource dataSource;

    public BaseEntity() {
        dataSource = DataSource.OpenWallet;
    }

    public BaseEntity(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
