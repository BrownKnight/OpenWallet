package com.openwallet.api.testutils;

import com.openwallet.api.data.models.Account;
import com.openwallet.api.data.models.Institution;

public final class TestDataFactory {
    private TestDataFactory() {
    }

    public static Institution createInstitution(String hint) {
        return new Institution(String.format("TEST_INSTITUTION_%s", hint));
    }

    public static Account createAccount(String hint) {
        return new Account(String.format("TEST_ACCOUNT_%s", hint), TestData.defaultInstitution);
    }

}
