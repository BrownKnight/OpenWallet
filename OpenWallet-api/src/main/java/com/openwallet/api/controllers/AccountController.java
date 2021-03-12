package com.openwallet.api.controllers;

import com.openwallet.api.data.models.Account;
import com.openwallet.api.data.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yapily.ApiException;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController extends CRUDController<Account, AccountService> {
    @PostMapping("/sync")
    public ResponseEntity<?> syncAllAccounts() throws ApiException {
        return ResponseEntity.ok(service.syncAllAccountsForCurrentUser());
    }
}
