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
import java.util.stream.Collectors;

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

    /**
     * Gets standing data from Yapily. Currently supports syncing all institutions we have access to.
     *
     * @throws ApiException From inner call to Yapily
     */
    public void synchroniseStandingData() throws ApiException {
        syncInstitutions();
    }

    private void syncInstitutions() throws ApiException {
        InstitutionsApi institutionsApi = new InstitutionsApi();
        List<yapily.sdk.Institution> yapilyInstitutions = institutionsApi.getInstitutionsUsingGET()
                .getData();

        List<Institution> owInstitutions = yapilyInstitutions.stream()
                .map(institution -> {
                    Institution mappedInstitution = new Institution(institution.getName(), institution.getId(),
                            DataSource.Yapily);

                    institution.getMedia()
                            .stream()
                            .filter(x -> x.getType()
                                    .equalsIgnoreCase("icon"))
                            .findFirst()
                            .ifPresent(media -> mappedInstitution.setIconUrl(media.getSource()));

                    institution.getMedia()
                            .stream()
                            .filter(x -> x.getType()
                                    .equalsIgnoreCase("logo"))
                            .findFirst()
                            .ifPresent(media -> mappedInstitution.setLogoUrl(media.getSource()));

                    // Check if the institution already exists
                    institutionService.findByExternalId(institution.getId())
                            .ifPresent(x -> mappedInstitution.setId(x.getId()));

                    return mappedInstitution;
                })
                .collect(Collectors.toList());


        institutionService.save(owInstitutions);
    }
}
