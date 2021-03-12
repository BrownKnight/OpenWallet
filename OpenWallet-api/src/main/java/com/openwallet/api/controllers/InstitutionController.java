package com.openwallet.api.controllers;

import com.openwallet.api.data.models.Institution;
import com.openwallet.api.data.services.InstitutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yapily.ApiException;

@RestController
@RequestMapping("/api/v1/institutions")
public class InstitutionController extends CRUDController<Institution, InstitutionService> {
    @PostMapping("/{institutionId}/accounts/sync")
    public ResponseEntity<?> syncAccounts(@PathVariable Long institutionId) throws ApiException {
        return ResponseEntity.ok(service.synchroniseAccountsForInstitution(institutionId));
    }
}
