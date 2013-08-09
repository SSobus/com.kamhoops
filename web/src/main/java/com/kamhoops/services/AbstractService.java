package com.kamhoops.services;

import com.kamhoops.data.domain.base.AbstractEntity;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.data.repository.base.BaseJpaRepository;
import com.kamhoops.exceptions.EntityValidationException;
import com.kamhoops.support.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * Abstract Service
 * <p/>
 * Base class for all services to encapsulate common functionality
 */
public abstract class AbstractService<REPO extends BaseJpaRepository<ENTITY>, ENTITY extends AbstractEntity> {

    @Autowired
    private EntityValidator validator;

    protected REPO repository;

    public List<ENTITY> findAll() {
        return repository.findAll();
    }

    protected void validateEntity(Object entity) throws EntityValidationException {
        BindingResult results = validator.validate(entity);

        if (results.hasErrors()) {
            throw new EntityValidationException(results.getAllErrors());
        }
    }

    public List<ENTITY> findAllActive() {
        return repository.findByActiveTrue();
    }

    public List<ENTITY> findAllAvailable() {
        return repository.findByDeletedFalse();
    }

    public ENTITY findById(Long id) throws EntityNotFoundException {
        if (id == null) {
            throw new EntityNotFoundException(getEntityTypeClass(), id);
        }

        ENTITY entity = repository.findByDeletedFalseAndId(id);

        if (entity == null) {
            throw new EntityNotFoundException(getEntityTypeClass(), id);
        }

        return entity;
    }

    public ENTITY toggleActiveById(Long id) throws EntityNotFoundException {
        ENTITY entity = findById(id);
        entity.setActive(!entity.isActive());

        return repository.save(entity);
    }

    public ENTITY deactivateById(Long id) throws EntityNotFoundException {
        ENTITY entity = findById(id);
        entity.setActive(false);

        return repository.save(entity);
    }

    public ENTITY deleteById(Long id) throws EntityNotFoundException, EntityValidationException {
        ENTITY entity = findById(id);
        entity.setDeleted(true);

        return repository.save(entity);
    }

    public abstract REPO getRepository();

    public abstract void setRepository(REPO repository);

    public abstract Class<ENTITY> getEntityTypeClass();
}
