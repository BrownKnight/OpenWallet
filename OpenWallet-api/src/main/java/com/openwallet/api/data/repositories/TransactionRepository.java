package com.openwallet.api.data.repositories;

import com.openwallet.api.data.models.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    @Query("select a from Transaction a where a.ownerId = ?#{ principal?.id }")
    List<Transaction> findAll();

    Transaction findById(long id);
}
