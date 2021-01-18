package com.openwallet.api.data.repositories;

import com.openwallet.api.data.models.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findById(long id);
}
