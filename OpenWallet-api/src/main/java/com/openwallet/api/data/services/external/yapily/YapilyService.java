package com.openwallet.api.data.services.external.yapily;

import com.openwallet.api.data.models.Institution;
import com.openwallet.api.data.models.responses.RedirectIntentionResponse;
import com.openwallet.api.data.models.responses.SimpleResponse;
import com.openwallet.api.data.models.responses.SuccessResponse;
import com.openwallet.api.data.models.types.DataSource;
import com.openwallet.api.data.services.AccountService;
import com.openwallet.api.data.services.MerchantService;
import com.openwallet.api.data.services.TransactionService;
import com.openwallet.api.util.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import yapily.ApiClient;
import yapily.ApiException;
import yapily.Configuration;
import yapily.auth.HttpBasicAuth;
import yapily.sdk.*;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class YapilyService {
    @Autowired
    public AccountService accountService;
    @Autowired
    public TransactionService transactionService;
    @Autowired
    public MerchantService merchantService;

    @Value("${yapily.api.key}")
    private String apiKey;
    @Value("${yapily.api.secret}")
    private String apiSecret;

    @PostConstruct
    public void init() {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        HttpBasicAuth basicAuth = (HttpBasicAuth) defaultClient.getAuthentication("basicAuth");
        basicAuth.setUsername(apiKey);
        basicAuth.setPassword(apiSecret);
    }

    /**
     * @param institution OW Institution where DataSource=Yapily to sync accounts for
     * @return RedirectIntentionResponse if consent is required. Otherwise,SuccessResponse with informative message
     * of how many accounts are synced
     * @throws ApiException From inner call to yapily API
     */
    public SimpleResponse syncYapilyAccountsForCurrentUser(Institution institution) throws ApiException {
        String applicationUserId = SecurityHelper.getCurrentUserId()
                .toString();
        // Check if we actually have consent for this institution
        ConsentsApi consentsApi = new ConsentsApi();
        Optional<Consent> consent = consentsApi.getConsentsUsingGET(Collections.singletonList(applicationUserId),
                Collections.emptyList(), Collections.singletonList(institution.getExternalId()),
                Collections.emptyList(), null, null, 1, null)
                .getData()
                .stream()
                .filter(c -> c.getStatus()
                        .equals(Consent.StatusEnum.AUTHORIZED))
                .findFirst();

        if (consent.isEmpty()) {
            String redirectUrl = this.requestConsentForInstitution(institution);
            return new RedirectIntentionResponse(
                    "No consent found for this institution. Please visit this URL to give us consent to access " +
                            "your data!",
                    redirectUrl);
        }

        String consentToken = consent.get()
                .getConsentToken();

        return this.syncAccounts(institution, consentToken);
    }

    private String requestConsentForInstitution(Institution institution) throws ApiException {
        final AccountsApi accountsApi = new AccountsApi();
        AccountAuthorisationRequest authRequest = new AccountAuthorisationRequest();
        authRequest.setApplicationUserId(SecurityHelper.getCurrentUserId()
                .toString());
        authRequest.setInstitutionId(institution.getExternalId());

        authRequest.setAccountRequest(new AccountRequest());
        ApiResponseOfAuthorisationRequestResponse authResponse = accountsApi.initiateAccountRequestUsingPOST(
                authRequest, null, null, null);
        return authResponse.getData()
                .getAuthorisationUrl();
    }

    private SimpleResponse syncAccounts(Institution institution, String consentToken) throws ApiException {
        final AccountsApi accountsApi = new AccountsApi();

        List<Account> accounts = accountsApi.getAccountsUsingGET(consentToken)
                .getData();
        List<com.openwallet.api.data.models.Account> mappedAccounts = accounts.stream()
                .map(account -> {
                    com.openwallet.api.data.models.Account mappedAccount = new com.openwallet.api.data.models.Account();
                    mappedAccount.setDataSource(DataSource.Yapily);
                    mappedAccount.setInstitution(institution);
                    mappedAccount.setCurrency(Currency.getInstance("GBP"));
                    mappedAccount.setBalance(account.getBalance() != null ? account.getBalance() : BigDecimal.ZERO);
                    mappedAccount.setName(account.getNickname() != null ? account.getNickname() : account.getType());
                    mappedAccount.setExternalId(account.getId());

                    // See if it already exists, and if so set the id
                    accountService.findByExternalId(account.getId())
                            .ifPresent((found) -> mappedAccount.setId(found.getId()));
                    return mappedAccount;
                })
                .collect(Collectors.toList());

        accountService.save(mappedAccounts);

        SimpleResponse response = new SuccessResponse(String.format("Imported %d accounts!", mappedAccounts.size()));
        for (com.openwallet.api.data.models.Account account : mappedAccounts) {
            response.addInnerResponse(syncTransactionsForAccount(account, consentToken));
        }

        return response;
    }

    /**
     * Given an OpenWallet which was created from a Yapily account, synchronise the last 1000 transactions from Yapily
     *
     * @param account      OW Account where DataSource=Yapily
     * @param consentToken Yapily consent token
     * @return SimpleResponse with message containing number of transactions synced
     * @throws ApiException thrown by inner call to Yapily api
     */
    private SimpleResponse syncTransactionsForAccount(com.openwallet.api.data.models.Account account,
                                                      String consentToken) throws ApiException {
        TransactionsApi transactionsApi = new TransactionsApi();
        List<Transaction> transactions = transactionsApi.getTransactionsUsingGET(consentToken, account.getExternalId(),
                Collections.emptyList(), "1980-01-01T00:00:00.000Z", null, null, null, 0, null)
                .getData();

        List<com.openwallet.api.data.models.Transaction> mappedTransactions = transactions.stream()
                .map(transaction -> {
                    com.openwallet.api.data.models.Transaction mappedTransaction =
                            new com.openwallet.api.data.models.Transaction();
                    mappedTransaction.setAccount(account);
                    mappedTransaction.setDataSource(DataSource.Yapily);
                    mappedTransaction.setExternalId(transaction.getId());
                    mappedTransaction.setDescription(transaction.getDescription());
                    mappedTransaction.setTransactionDate(Date.from(transaction.getDate()
                            .toInstant()));
                    mappedTransaction.setAmount(transaction.getAmount());
                    mappedTransaction.setMerchant(
                            merchantService.findOrCreateByYapilyMerchant(transaction.getMerchant()));


                    // See if it already exists, and if so set the id
                    transactionService.findByExternalIdAndAccountId(transaction.getId(), account.getId())
                            .ifPresent((found) -> mappedTransaction.setId(found.getId()));
                    return mappedTransaction;
                })
                .collect(Collectors.toList());

        transactionService.save(mappedTransactions);
        return new SuccessResponse(String.format("Synced %d transactions", mappedTransactions.size()));
    }
}
