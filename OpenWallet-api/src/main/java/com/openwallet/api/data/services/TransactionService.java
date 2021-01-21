package com.openwallet.api.data.services;

import com.openwallet.api.data.models.Transaction;
import com.openwallet.api.data.repositories.TransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class TransactionService extends CRUDService<Transaction, TransactionRepository> {

}
