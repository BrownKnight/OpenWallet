package com.openwallet.api.data.services.external.yapily;

import com.openwallet.api.data.models.Institution;
import com.openwallet.api.data.models.types.DataSource;
import com.openwallet.api.data.services.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import yapily.ApiClient;
import yapily.ApiException;
import yapily.Configuration;
import yapily.auth.HttpBasicAuth;
import yapily.sdk.InstitutionsApi;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class YapilyStandingDataService {
    @Autowired
    InstitutionService institutionService;
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

    public void synchroniseStandingData() throws ApiException {
        syncInstitutions();
    }

    private void syncInstitutions() throws ApiException {
        InstitutionsApi institutionsApi = new InstitutionsApi();
        List<yapily.sdk.Institution> yapilyInstitutions = institutionsApi.getInstitutionsUsingGET()
                .getData();

        Iterable<Institution> existingInstitutions = institutionService.findAll();

        List<Institution> owInstitutions = yapilyInstitutions.stream()
                .map(institution -> new Institution(institution.getName(), institution.getId(), DataSource.Yapily))
                .peek(institution -> {
                    // See if there is an existing institutions with this name, and set the id so it updates instead
                    // of inserts
                    Optional<Institution> existing = StreamSupport.stream(existingInstitutions.spliterator(), false)
                            .filter(x -> x.getName()
                                    .equals(institution.getName()))
                            .findFirst();

                    existing.ifPresent(value -> institution.setId(value.getId()));
                })
                .collect(Collectors.toList());


        institutionService.save(owInstitutions);
    }
}
