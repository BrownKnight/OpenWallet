package com.openwallet.api.controllers;

import com.openwallet.api.data.models.Account;
import com.openwallet.api.data.services.AccountService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController extends CRUDController<Account, AccountService> {

}
