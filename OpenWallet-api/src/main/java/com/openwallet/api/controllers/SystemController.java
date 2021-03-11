package com.openwallet.api.controllers;

import com.openwallet.api.data.models.responses.ErrorResponse;
import com.openwallet.api.data.models.responses.SuccessResponse;
import com.openwallet.api.data.services.external.yapily.YapilyStandingDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yapily.ApiException;

@RestController
@RequestMapping("/api/v1/system")
public class SystemController extends BaseController {
    @Autowired
    YapilyStandingDataService yapilyStandingDataService;

    @PostMapping("/yapily/sync")
    public ResponseEntity<?> yapilySynchroniseStandingData() {
        try {
            yapilyStandingDataService.synchroniseStandingData();
        } catch (ApiException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Failed to synchronise standing data"));
        }

        return ResponseEntity.ok(new SuccessResponse("Yapily standing data synchronised"));
    }
}
