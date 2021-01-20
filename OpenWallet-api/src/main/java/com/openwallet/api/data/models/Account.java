package com.openwallet.api.data.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Currency;

@Entity
public class Account extends UserScopedEntity {
    private String name;
    @ManyToOne(targetEntity = Institution.class)
    private Institution institution;
    private BigDecimal balance;
    private Currency currency;
    @OneToMany(targetEntity = Transaction.class)
    private Collection<Transaction> transactions;

    public Account(String name, Institution institution, Currency currency) {
        this.name = name;
        this.institution = institution;
        this.currency = currency;
    }


    public Account() {
    }

    public Collection<Transaction> getTransactions() {
        return transactions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }
}
