package com.openwallet.api.controllers;

import com.openwallet.api.data.models.Institution;
import com.openwallet.api.data.service.InstitutionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/institutions")
public class InstitutionController extends CRUDController<Institution, InstitutionService> {

}
