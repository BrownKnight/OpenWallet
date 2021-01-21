package com.openwallet.api.testutils;

public class Endpoints {
    public static final String INSTITUTIONS = "/api/v1/institutions";
    public static final String ACCOUNTS = "/api/v1/accounts";
    public static final String TRANSACTIONS = "/api/v1/transactions";
    public static final String USERS = "/api/v1/users";
    public static final String LOGIN = "/api/v1/login";
    public static final String REGISTER = "/api/v1/login/register";

    public static String AccountByAccountId(Long accountId) {
        return String.format("%s/%s", Endpoints.ACCOUNTS, accountId);
    }

    public static String TransactionsForAccount(Long accountId) {
        return String.format("%s/transactions", Endpoints.AccountByAccountId(accountId));
    }
}
