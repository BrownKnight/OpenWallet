package com.openwallet.api.data.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Currency;

@Entity
@NoArgsConstructor
public class Account extends UserScopedEntity {
    @Getter
    @Setter
    @Column(nullable = false)
    private String name;

    @Getter
    @Setter
    @ManyToOne(targetEntity = Institution.class, optional = false)
    private Institution institution;

    @Getter
    @Setter
    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Getter
    @Setter
    @Column(nullable = false)
    private Currency currency;

    @Getter
    @OneToMany(targetEntity = Transaction.class, fetch = FetchType.LAZY, mappedBy = "account", cascade =
            CascadeType.ALL)
    private Collection<Transaction> transactions;

    public Account(String name, Institution institution, Currency currency) {
        this.name = name;
        this.institution = institution;
        this.currency = currency;
    }
}
