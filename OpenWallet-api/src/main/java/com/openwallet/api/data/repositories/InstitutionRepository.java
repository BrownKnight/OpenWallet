package com.openwallet.api.data.repositories;

import com.openwallet.api.data.models.Institution;
import org.springframework.data.repository.CrudRepository;

public interface InstitutionRepository extends CrudRepository<Institution, Long> {
    Institution findById(long id);
}
