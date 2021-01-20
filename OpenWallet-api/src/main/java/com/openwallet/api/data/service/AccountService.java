package com.openwallet.api.data.service;

import com.openwallet.api.data.models.Account;
import com.openwallet.api.data.models.Transaction;
import com.openwallet.api.data.repositories.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AccountService extends CRUDService<Account, AccountRepository> {
    public Collection<Transaction> getTransactionsForAccount(Long accountId) {
        return repository.findById(accountId).orElseThrow()
                .getTransactions();
    }
}
