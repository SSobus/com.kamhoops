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
public abstract class AbstractService<T extends BaseJpaRepository<E>, E extends AbstractEntity> {

    @Autowired
    private EntityValidator validator;

    protected T repository;

    public List<E> findAll() {
        return repository.findAll();
    }

    protected void validateEntity(Object entity) throws EntityValidationException {
        BindingResult results = validator.validate(entity);

        if (results.hasErrors()) {
            throw new EntityValidationException(results.getAllErrors());
        }
    }

    public List<E> findAllActive() {
        return repository.findByActiveTrue();
    }

    public List<E> findAllAvailable() {
        return repository.findByDeletedFalse();
    }

    public E findById(Long id) throws EntityNotFoundException {
        if (id == null) {
            throw new EntityNotFoundException(getEntityTypeClass(), id);
        }

        E entity = repository.findByDeletedFalseAndId(id);

        if (entity == null) {
            throw new EntityNotFoundException(getEntityTypeClass(), id);
        }

        return entity;
    }

    public E toggleActiveById(Long id) throws EntityNotFoundException {
        E entity = findById(id);
        entity.setActive(!entity.isActive());

        return repository.save(entity);
    }

    public E deactivateById(Long id) throws EntityNotFoundException {
        E entity = findById(id);
        entity.setActive(false);

        return repository.save(entity);
    }

    public E deleteById(Long id) throws EntityNotFoundException, EntityValidationException {
        E entity = findById(id);
        entity.setDeleted(true);

        return repository.save(entity);
    }

    public abstract T getRepository();

    public abstract void setRepository(T repository);

    public abstract Class<E> getEntityTypeClass();
}
