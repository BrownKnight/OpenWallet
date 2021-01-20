package com.openwallet.api.data.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public class Transaction extends UserScopedEntity {
    private String description;
    @ManyToOne(targetEntity = Account.class)
    private Account account;
    // Positive = Debit, Negative = Credit
    private BigDecimal amount;

    public Transaction(String description, Account account) {
        this.description = description;
        this.account = account;
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

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
