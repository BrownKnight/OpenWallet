package com.openwallet.api.data.repositories;

import com.openwallet.api.data.models.Account;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface AccountRepository extends EntityRepository<Account> {
    @Query("select a from Account a where a.ownerId = ?#{ principal?.id }")
    Collection<Account> findAll();
}
