package com.openwallet.api.data.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Account extends BaseEntity {
    private String name;
    @ManyToOne(targetEntity = Institution.class)
    private Institution institution;


    public Account(String name, Institution institution) {
        this.name = name;
        this.institution = institution;
    }

    public Account() {
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
}
