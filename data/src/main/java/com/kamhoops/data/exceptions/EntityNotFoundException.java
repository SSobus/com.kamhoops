package com.kamhoops.data.exceptions;

import com.kamhoops.data.domain.base.AbstractEntity;

/**
 * Entity Not Found Exception
 * <p/>
 * Thrown when an entity could not be found
 */
public class EntityNotFoundException extends Exception {
    private Class<? extends AbstractEntity> entityClass;

    private String entityId;

    public EntityNotFoundException(Class<? extends AbstractEntity> entityClass, Long id) {
        assert (entityClass != null);

        this.entityClass = entityClass;
        this.entityId = (id != null ? id.toString() : null);
    }

    public EntityNotFoundException(Class<? extends AbstractEntity> entityClass, String entityId) {
        assert (entityClass != null);

        this.entityClass = entityClass;
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityClass.getSimpleName();
    }

    public String getEntityId() {
        return entityId;
    }

    @Override
    public String getMessage() {
        return String.format("Entity of type '%s' by identifier: '%s' was not found", getEntityName(), (entityId == null ? "null" : entityId));
    }
}
