package com.openwallet.api.data.service;

import com.openwallet.api.data.models.BaseEntity;
import com.openwallet.api.util.ObjectPropertyHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public class CRUDService<TEntity extends BaseEntity, TRepository extends CrudRepository<TEntity, Long>> {
    @Autowired
    protected TRepository repository;

    public Iterable<TEntity> findAll() {
        return repository.findAll();
    }

    public Optional<TEntity> findById(long id) {
        return repository.findById(id);
    }

    public TEntity save(TEntity entity) {
        if (entity.getId() != null) {
            TEntity existingEntity = repository.findById(entity.getId())
                    .orElseThrow();
            ObjectPropertyHelpers.copyNonNullProperties(entity, existingEntity);
            return repository.save(existingEntity);

        } else {

            return repository.save(entity);
        }
    }

    public Iterable<TEntity> save(Iterable<TEntity> entities) {
        return repository.saveAll(entities);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }
}