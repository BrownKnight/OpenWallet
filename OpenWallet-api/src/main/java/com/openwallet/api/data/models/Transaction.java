package com.openwallet.api.data.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@NoArgsConstructor
public class Transaction extends UserScopedEntity {
    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY, optional = false)
    // To prevent infinite nesting due to double sided relations
    @JsonIgnoreProperties({"transactions", "institution"})
    private Account account;

    // Positive = Debit, Negative = Credit
    @Getter
    @Setter
    @Column(nullable = false)
    private BigDecimal amount;

    @Getter
    @Setter
    @Column(nullable = false)
    private Date transactionDate;

    @Getter
    @Setter
    @ManyToOne(targetEntity = Merchant.class, optional = false)
    private Merchant merchant;

    public Transaction(String description, Account account, BigDecimal amount, Date transactionDate) {
        this.description = description;
        this.account = account;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }
}
