package com.openwallet.api.controllers;

import com.openwallet.api.data.models.Account;
import com.openwallet.api.data.models.Transaction;
import com.openwallet.api.testutils.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

public class TransactionControllerIT extends BaseIntegrationTest {
    @Test
    public void CanAddNewTransactionsToAccount() throws Exception {
        Transaction newTransaction = TestDataFactory.createTransaction("TEST1", TestData.defaultAccountUserA,
                BigDecimal.TEN);

        Transaction returnedTransaction = TestUtils.readResponseAs(As.UserA()
                .putRequest(Endpoints.TransactionsForAccount(TestData.defaultAccountUserA.getId()), newTransaction)
                .andExpect(status().isOk())
                .andReturn(), Transaction.class);

        assertNotNull(returnedTransaction);
        assertEquals(As.UserA()
                .getUserId(), returnedTransaction.getOwnerId());
        assertEquals(TestData.defaultAccountUserA.getId(), returnedTransaction.getAccount()
                .getId());
    }

    @Test
    public void CanFetchTransactionRelatedToAccount() throws Exception {
        Account accountTransactions = TestUtils.readResponseAs(As.UserA()
                .getRequest(
                        String.format("%s/%s/transactions", Endpoints.ACCOUNTS, TestData.defaultAccountUserA.getId()))
                .andExpect(status().isOk())
                .andReturn(), Account.class);

        assertNotNull(accountTransactions);
        assertNotNull(accountTransactions.getTransactions());
    }

    @Test
    public void NewTransactionUpdatesAccountBalance() throws Exception {
        Transaction newTransaction = TestDataFactory.createTransaction("TEST1", TestData.defaultAccountUserA,
                BigDecimal.TEN);

        As.UserA()
                .putRequest(Endpoints.TransactionsForAccount(TestData.defaultAccountUserA.getId()), newTransaction)
                .andExpect(status().isOk());

        Account returnedAccount = TestUtils.readResponseAs(As.UserA()
                .getRequest(
                        String.format("%s/%s/transactions", Endpoints.ACCOUNTS, TestData.defaultAccountUserA.getId()))
                .andExpect(status().isOk())
                .andReturn(), Account.class);

        BigDecimal expectedNewValue = TestData.defaultAccountUserA.getBalance()
                .add(newTransaction.getAmount());

        assertNotNull(returnedAccount);
        assertEquals(expectedNewValue.compareTo(returnedAccount.getBalance()), 0);

        // Add another transaction
        As.UserA()
                .putRequest(Endpoints.TransactionsForAccount(TestData.defaultAccountUserA.getId()), newTransaction)
                .andExpect(status().isOk());

        returnedAccount = TestUtils.readResponseAs(As.UserA()
                .getRequest(
                        String.format("%s/%s/transactions", Endpoints.ACCOUNTS, TestData.defaultAccountUserA.getId()))
                .andExpect(status().isOk())
                .andReturn(), Account.class);

        expectedNewValue = expectedNewValue.add(newTransaction.getAmount());

        assertNotNull(returnedAccount);
        assertEquals(expectedNewValue.compareTo(returnedAccount.getBalance()), 0);
    }
}
