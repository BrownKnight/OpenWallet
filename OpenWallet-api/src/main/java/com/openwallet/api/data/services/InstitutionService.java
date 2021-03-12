package com.openwallet.api.data.services;

import com.openwallet.api.data.models.Institution;
import com.openwallet.api.data.models.responses.SimpleResponse;
import com.openwallet.api.data.models.responses.SuccessResponse;
import com.openwallet.api.data.repositories.InstitutionRepository;
import com.openwallet.api.data.services.external.yapily.YapilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yapily.ApiException;

@Component
public class InstitutionService extends CRUDService<Institution, InstitutionRepository> {
    @Autowired
    YapilyService yapilyService;

    public SimpleResponse synchroniseAccountsForInstitution(Long institutionId) throws ApiException {
        Institution institution = this.findById(institutionId)
                .orElseThrow();

        return switch (institution.getDataSource()) {
            case Yapily -> yapilyService.syncYapilyAccountsForCurrentUser(institution);
            case OpenWallet -> new SuccessResponse("OpenWallet Accounts do not need to synchronise!");
        };
    }


}
