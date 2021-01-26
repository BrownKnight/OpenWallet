package com.openwallet.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openwallet.api.data.models.Transaction;
import com.openwallet.api.data.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/accounts")
public class TransactionController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<?> getTransactionsForAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.getTransactionsForAccount(accountId));
    }

    @PutMapping("/{accountId}/transactions")
    public ResponseEntity<?> addTransactionToAccount(@PathVariable Long accountId, @RequestBody String requestBody) {
        ObjectMapper mapper = new ObjectMapper();
        // To allow saving of either single or an array of transactions, parse the string
        try {
            Transaction transaction = mapper.readValue(requestBody, Transaction.class);
            return ResponseEntity.ok(accountService.addTransactionToAccount(accountId, transaction));
        } catch (JsonProcessingException e1) {
            try {
                //noinspection unchecked
                ArrayList<Transaction> transactions = mapper.readValue(requestBody, ArrayList.class);
                return ResponseEntity.ok(accountService.addTransactionToAccount(accountId, transactions));
            } catch (JsonProcessingException e2) {
                throw new IllegalArgumentException("Request body if not a list of or a single transaction.");
            }
        }
    }
}
