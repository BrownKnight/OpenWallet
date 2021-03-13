package com.openwallet.api.data.services;

import com.openwallet.api.data.models.Transaction;
import com.openwallet.api.data.repositories.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TransactionService extends CRUDService<Transaction, TransactionRepository> {
    public Optional<Transaction> findByExternalIdAndAccountId(String externalId, Long accountId) {
        return repository.findByExternalIdAndAccountId(externalId, accountId);
    }
}
