package com.openwallet.api.data.services;

import com.openwallet.api.data.models.Account;
import com.openwallet.api.data.models.Institution;
import com.openwallet.api.data.models.Transaction;
import com.openwallet.api.data.repositories.AccountRepository;
import com.openwallet.api.util.ObjectPropertyHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountService extends CRUDService<Account, AccountRepository> {
    @Autowired
    TransactionService transactionService;

    @Autowired
    InstitutionService institutionService;

    public Account getTransactionsForAccount(Long accountId) {
        Account account = repository.findById(accountId)
                .orElseThrow();
        // Load the transactions (lazy loaded)
        //noinspection ResultOfMethodCallIgnored
        account.getTransactions();
        return account;
    }

    public Transaction addTransactionToAccount(Long accountId, Transaction transaction) {
        Account account = repository.findById(accountId)
                .orElseThrow();
        transaction.setAccount(account);

        account.setBalance(account.getBalance()
                .add(transaction.getAmount()));

        transactionService.save(transaction);

        return transaction;
    }

    @Override
    public Account save(Account entity) {
        if (entity.getId() != null) {
            Account existingEntity = repository.findById(entity.getId())
                    .orElseThrow();
            ObjectPropertyHelpers.copyNonNullProperties(entity, existingEntity);
            entity = existingEntity;
        }

        if (entity.getInstitution() == null) {
            throw new IllegalArgumentException("Institution not provided");
        }

        Institution institution = institutionService.findById(entity.getInstitution()
                .getId())
                .orElseThrow();

        entity.setInstitution(institution);

        return repository.save(entity);
    }
}
