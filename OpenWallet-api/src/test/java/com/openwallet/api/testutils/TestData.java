package com.openwallet.api.testutils;

import com.openwallet.api.data.models.Account;
import com.openwallet.api.data.models.Institution;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public final class TestData {
    public static Institution defaultInstitution;
    public static Account defaultAccountUserA;
    public static Account defaultAccountUserB;

    public static void initIntegrationData(MockMvc mockMvc) throws Exception {
        As.setMockMvc(mockMvc);

        // init Institutions
        defaultInstitution = TestUtils.readResponseAs(As.Admin()
                .putRequest(Endpoints.INSTITUTIONS, TestDataFactory.createInstitution("Bank o' Money"))
                .andExpect(status().isOk())
                .andReturn(), Institution.class);

        // init Accounts
        defaultAccountUserA = TestUtils.readResponseAs(As.UserA()
                .putRequest(Endpoints.ACCOUNTS, TestDataFactory.createAccount("A1"))
                .andExpect(status().isOk())
                .andReturn(), Account.class);

        defaultAccountUserB = TestUtils.readResponseAs(As.UserB()
                .putRequest(Endpoints.ACCOUNTS, TestDataFactory.createAccount("B1"))
                .andExpect(status().isOk())
                .andReturn(), Account.class);
    }
}
