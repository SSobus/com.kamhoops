package com.kamhoops.exceptions;

import com.kamhoops.data.domain.base.AbstractEntity;
import org.springframework.validation.FieldError;

/**
 * Thrown when an entity cannot be create due to a unique constraint on a given field
 */
public class DuplicateEntityException extends EntityValidationException {
    private AbstractEntity entity;
    private String entityName;
    private String duplicationOnFieldName;

    public DuplicateEntityException(AbstractEntity entity, String entityName, String duplicationOnFieldName) {
        super(new FieldError(entityName, duplicationOnFieldName, String.format("Cannot create entity '%s' because it conflicts with field '%s' on entity id '%d'", entityName, duplicationOnFieldName, entity.getId())));

        this.entity = entity;
        this.entityName = entityName;
        this.duplicationOnFieldName = duplicationOnFieldName;
    }

    public AbstractEntity getEntity() {
        return entity;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getDuplicationOnFieldName() {
        return duplicationOnFieldName;
    }
}
