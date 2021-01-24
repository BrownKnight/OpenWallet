package com.openwallet.api.data.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Transaction extends UserScopedEntity {
    private String description;
    @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY, optional = false)
    @JsonIgnoreProperties("transactions")
    private Account account;
    // Positive = Debit, Negative = Credit
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private Date transactionDate;

    public Transaction(String description, Account account, BigDecimal amount, Date transactionDate) {
        this.description = description;
        this.account = account;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Transaction() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }
}
