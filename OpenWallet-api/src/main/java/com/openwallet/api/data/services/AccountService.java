package com.openwallet.api.data.services;

import com.openwallet.api.data.models.Account;
import com.openwallet.api.data.models.Institution;
import com.openwallet.api.data.models.Transaction;
import com.openwallet.api.data.models.responses.RedirectIntentionResponse;
import com.openwallet.api.data.models.responses.SimpleResponse;
import com.openwallet.api.data.models.responses.SuccessResponse;
import com.openwallet.api.data.repositories.AccountRepository;
import com.openwallet.api.util.ObjectPropertyHelpers;
import com.openwallet.api.util.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yapily.ApiException;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

        if (entity.getCurrency() == null) {
            entity.setCurrency(Currency.getInstance("GBP"));
        }

        return repository.save(entity);
    }

    public Iterable<Transaction> addTransactionToAccount(Long accountId, Iterable<Transaction> transactions) {
        List<Transaction> savedTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            savedTransactions.add(this.addTransactionToAccount(accountId, transaction));
        }

        return savedTransactions;
    }

    public SimpleResponse syncAllAccountsForCurrentUser() throws ApiException {
        Long currentUserId = SecurityHelper.getCurrentUserId();
        Iterable<Account> allUsersAccounts = this.findAll();
        List<Institution> allUsersInstitutions = StreamSupport.stream(allUsersAccounts.spliterator(), false)
                .map(Account::getInstitution)
                .distinct()
                .collect(Collectors.toList());


        SimpleResponse response = new SuccessResponse(
                String.format("Synchronised accounts for %d institutions", allUsersInstitutions.size()));
        for (Institution institution : allUsersInstitutions) {
            response.addInnerResponse(institutionService.synchroniseAccountsForInstitution(institution.getId()));
        }

        Optional<SimpleResponse> redirectRequired = response.getInnerResponses()
                .stream()
                .filter(res -> res instanceof RedirectIntentionResponse)
                .findFirst();

        if (redirectRequired.isPresent() && redirectRequired.get() instanceof RedirectIntentionResponse) {
            return redirectRequired.get();
        }

        return response;
    }
}
