package com.openwallet.api.controllers;

import com.openwallet.api.data.models.Account;
import com.openwallet.api.data.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController extends CRUDController<Account, AccountService> {
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<?> getTransactionForAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(service.getTransactionsForAccount(accountId));
    }
}
