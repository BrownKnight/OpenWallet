package com.openwallet.api.data.services;

import com.openwallet.api.data.models.BaseEntity;
import com.openwallet.api.data.models.UserLogin;
import com.openwallet.api.data.models.UserScopedEntity;
import com.openwallet.api.data.models.listeners.UnauthorisedEntityAccessException;
import com.openwallet.api.data.models.responses.SimpleResponse;
import com.openwallet.api.data.models.responses.SuccessResponse;
import com.openwallet.api.data.repositories.EntityRepository;
import com.openwallet.api.util.ObjectPropertyHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class CRUDService<TEntity extends BaseEntity, TRepository extends EntityRepository<TEntity>> {
    @Autowired
    protected TRepository repository;

    public Iterable<TEntity> findAll() {
        return repository.findAll();
    }

    public Optional<TEntity> findById(long id) {
        Optional<TEntity> returnedEntity = repository.findById(id);
        if (returnedEntity.isPresent() && returnedEntity.get() instanceof UserScopedEntity) {
            Long ownerId = ((UserScopedEntity) returnedEntity.get()).getOwnerId();
            Long currentUserId = ((UserLogin) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal()).getId();
            if (!ownerId.equals(currentUserId)) {
                throw new UnauthorisedEntityAccessException("Tried to fetch an entity owned by another user!");
            }
        }
        return returnedEntity;
    }

    public Optional<TEntity> findByExternalId(String externalId) {
        Optional<TEntity> returnedEntity = repository.findByExternalId(externalId);
        if (returnedEntity.isPresent() && returnedEntity.get() instanceof UserScopedEntity) {
            Long ownerId = ((UserScopedEntity) returnedEntity.get()).getOwnerId();
            Long currentUserId = ((UserLogin) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal()).getId();
            if (!ownerId.equals(currentUserId)) {
                throw new UnauthorisedEntityAccessException("Tried to fetch an entity owned by another user!");
            }
        }
        return returnedEntity;
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

    public SimpleResponse deleteById(long id) {
        repository.deleteById(id);
        return new SuccessResponse(String.format("Delete entity with ID %d", id));
    }
}
