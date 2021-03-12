package com.openwallet.api.data.services.external.yapily;

import com.openwallet.api.data.models.Institution;
import com.openwallet.api.data.models.responses.RedirectIntentionResponse;
import com.openwallet.api.data.models.responses.SimpleResponse;
import com.openwallet.api.data.models.responses.SuccessResponse;
import com.openwallet.api.data.models.types.DataSource;
import com.openwallet.api.data.services.AccountService;
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
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class YapilyService {
    @Autowired
    public AccountService accountService;
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

    public String requestConsentForInstitution(Institution institution) throws ApiException {
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

        final AccountsApi accountsApi = new AccountsApi();
        if (consent.isEmpty()) {
            AccountAuthorisationRequest authRequest = new AccountAuthorisationRequest();
            authRequest.setApplicationUserId(applicationUserId);
            authRequest.setInstitutionId(institution.getExternalId());
            authRequest.setAccountRequest(new AccountRequest());
            ApiResponseOfAuthorisationRequestResponse authResponse = accountsApi.initiateAccountRequestUsingPOST(
                    authRequest, null, null, null);

            String redirectUrl = authResponse.getData()
                    .getAuthorisationUrl();
            return new RedirectIntentionResponse(
                    "No consent found for this institution. Please visit this URL to give us consent to access " +
                            "your data!",
                    redirectUrl);
        }

        String consentToken = consent.get()
                .getConsentToken();
        List<Account> accounts = accountsApi.getAccountsUsingGET(consentToken)
                .getData();
        return this.importAccounts(accounts, institution);
    }


    public SimpleResponse importAccounts(List<yapily.sdk.Account> accounts, Institution institution) {
        List<com.openwallet.api.data.models.Account> mappedAccounts = accounts.stream()
                .map(account -> {
                    com.openwallet.api.data.models.Account mappedAccount = new com.openwallet.api.data.models.Account();
                    mappedAccount.setDataSource(DataSource.Yapily);
                    mappedAccount.setInstitution(institution);
                    mappedAccount.setCurrency(Currency.getInstance("GBP"));
                    mappedAccount.setBalance(account.getBalance());
                    mappedAccount.setName(account.getNickname());

                    // See if it already exists, and if so set the id
                    accountService.findByExternalId(account.getId())
                            .ifPresent((found) -> mappedAccount.setId(found.getId()));
                    return mappedAccount;
                })
                .collect(Collectors.toList());

        accountService.save(mappedAccounts);

        return new SuccessResponse(String.format("Imported %d accounts!", mappedAccounts.size()));
    }
}
