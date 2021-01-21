package com.openwallet.api.controllers;

import com.openwallet.api.data.models.Account;
import com.openwallet.api.data.models.Transaction;
import com.openwallet.api.testutils.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AccountControllerIT extends BaseIntegrationTest {
    @Test
    public void NewAccountIsOwnedByCurrentUser() throws Exception {
        Account newAccount = TestDataFactory.createAccount("OwnedByUserA");

        Account returnedAccount = TestUtils.readResponseAs(As.UserA()
                .putRequest(Endpoints.ACCOUNTS, newAccount)
                .andExpect(status().isOk())
                .andReturn(), Account.class);

        assertEquals(As.UserA()
                .getUserId(), returnedAccount.getOwnerId());
    }

    @Test
    public void UsersCanOnlyFetchTheirOwnAccountById() throws Exception {
        Account userAAccount = TestData.defaultAccountUserA;
        Account userBAccount = TestData.defaultAccountUserB;

        Account fetchedUserAAccount = TestUtils.readResponseAs(As.UserA()
                .getRequest(Endpoints.ACCOUNTS + "/" + userAAccount.getId())
                .andReturn(), Account.class);

        assertNotNull(fetchedUserAAccount);
        assertEquals(fetchedUserAAccount.getId(), userAAccount.getId());

        As.UserA()
                .getRequest(Endpoints.ACCOUNTS + "/" + userBAccount.getId())
                .andExpect(status().isForbidden());
    }

    @Test
    public void UsersCanOnlyFetchTheirOwnAccounts() throws Exception {
        Account[] fetchedAccounts = TestUtils.readResponseAs(As.UserA()
                .getRequest(Endpoints.ACCOUNTS)
                .andReturn(), Account[].class);

        assertNotNull(fetchedAccounts);
        for (Account account : fetchedAccounts) {
            assertEquals(account.getOwnerId(), As.UserA()
                    .getUserId());
        }
    }

    @Test
    public void CannotUpdateAnotherUsersAccount() throws Exception {
        Account userBAccount = TestData.defaultAccountUserB;
        String originalName = userBAccount.getName();
        String newName = "TEST_NAME_CHANGED";
        userBAccount.setName(newName);

        // User A tries to update the account, should return FORBIDDEN
        As.UserA()
                .putRequest(Endpoints.ACCOUNTS, userBAccount)
                .andExpect(status().isForbidden());

        // User B fetches their account, should be same as original
        Account updatedAccount = TestUtils.readResponseAs(As.UserB()
                .getRequest(Endpoints.ACCOUNTS + "/" + userBAccount.getId())
                .andReturn(), Account.class);

        assertNotNull(updatedAccount);
        assertEquals(updatedAccount.getName(), originalName);
    }
}
