package com.openwallet.api.controllers;

import com.openwallet.api.data.models.Transaction;
import com.openwallet.api.data.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class TransactionController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<?> getTransactionForAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.getTransactionsForAccount(accountId));
    }

    @PutMapping("/{accountId}/transactions")
    public ResponseEntity<?> addTransactionToAccount(@PathVariable Long accountId, @RequestBody Transaction transaction) {
        return ResponseEntity.ok(accountService.addTransactionToAccount(accountId, transaction));
    }
}
