package com.openwallet.api.data.repositories;

import com.openwallet.api.data.models.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface AccountRepository extends CrudRepository<Account, Long> {
    @Query("select a from Account a where a.ownerId = ?#{ principal?.id }")
    Collection<Account> findAll();

    Account findById(long id);
}
