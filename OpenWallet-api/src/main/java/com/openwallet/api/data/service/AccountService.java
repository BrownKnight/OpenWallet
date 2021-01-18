package com.openwallet.api.data.service;

import com.openwallet.api.data.models.Account;
import com.openwallet.api.data.models.User;
import com.openwallet.api.data.repositories.AccountRepository;
import com.openwallet.api.data.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class AccountService extends CRUDService<Account, AccountRepository> {

}
