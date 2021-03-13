package com.openwallet.api.data.repositories;

import com.openwallet.api.data.models.Transaction;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends EntityRepository<Transaction> {
    @Query("select a from Transaction a where a.ownerId = ?#{ principal?.id }")
    List<Transaction> findAll();

    Optional<Transaction> findByExternalIdAndAccountId(String externalId, Long accountId);
}
