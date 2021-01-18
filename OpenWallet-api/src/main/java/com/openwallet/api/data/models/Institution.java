package com.openwallet.api.data.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Institution extends UserScopedEntity {
    private String name;
    @ManyToOne(targetEntity = Account.class)
    private Account[] accounts;

    public Institution(String name) {
        this.name = name;
    }

    public Institution() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account[] getAccount() {
        return accounts;
    }

    public void setAccount(Account[] accounts) {
        this.accounts = accounts;
    }
}
