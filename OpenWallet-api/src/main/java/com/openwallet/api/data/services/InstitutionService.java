package com.openwallet.api.data.services;

import com.openwallet.api.data.models.Institution;
import com.openwallet.api.data.repositories.InstitutionRepository;
import org.springframework.stereotype.Component;

@Component
public class InstitutionService extends CRUDService<Institution, InstitutionRepository> {

}