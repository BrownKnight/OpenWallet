package com.openwallet.api.testutils;

import com.openwallet.api.data.models.Account;
import com.openwallet.api.data.models.Institution;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public final class TestData {
    public static Institution defaultInstitution;
    public static Account defaultAccount;

    public static void initIntegrationData(MockMvc mockMvc) throws Exception {
        As.setMockMvc(mockMvc);

        // init Institutions
        defaultInstitution = TestUtils.readResponseAs(As.Admin()
                .putRequest(Endpoints.INSTITUTIONS, TestDataFactory.createInstitution("A"))
                .andExpect(status().isOk())
                .andReturn(), Institution.class);

        // init Accounts
        defaultAccount = TestUtils.readResponseAs(As.Admin()
                .putRequest(Endpoints.ACCOUNTS, TestDataFactory.createAccount("A"))
                .andExpect(status().isOk())
                .andReturn(), Account.class);
    }
}
