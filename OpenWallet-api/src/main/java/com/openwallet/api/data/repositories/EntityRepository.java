package com.openwallet.api.data.repositories;

import com.openwallet.api.data.models.BaseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface EntityRepository<TEntity extends BaseEntity> extends CrudRepository<TEntity, Long> {
    Optional<TEntity> findById(long id);

    Optional<TEntity> findByExternalId(String externalId);
}
