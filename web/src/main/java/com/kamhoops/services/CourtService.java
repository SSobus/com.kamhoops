package com.kamhoops.services;

import com.kamhoops.data.domain.Court;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.data.repository.CourtRepository;
import com.kamhoops.exceptions.EntityValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CourtService extends AbstractService<CourtRepository, Court> {

    public Court create(Court court) throws EntityValidationException, EntityNotFoundException {
        preCreateChecks(court);

        court = repository.save(court);

        return findById(court.getId());
    }

    private void preCreateChecks(Court court) throws EntityValidationException {
        Assert.notNull(court, "Supplied gameTime cannot be null");
        Assert.isNull(court.getId(), "Supplied gameTime cannot have an Id, are you sure this object should be created");

        validateEntity(court);
    }

    public Court update(Court court) throws EntityValidationException, EntityNotFoundException {
        court = preUpdateChecksAndMerges(court);

        court = repository.save(court);

        return findById(court.getId());
    }

    private Court preUpdateChecksAndMerges(Court court) throws EntityValidationException, EntityNotFoundException {
        Assert.notNull(court, "Supplied gameTime cannot be null");
        Assert.notNull(court.getId(), "Supplied gameTime does not have an Id, are you sure this object should be updated");

        validateEntity(court);

        Court existingCourt = findById(court.getId());

        //merge changes into existing entity
        existingCourt.setName(court.getName());

        validateEntity(existingCourt);

        return existingCourt;
    }

    @Override
    public CourtRepository getRepository() {
        return repository;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Autowired
    public void setRepository(CourtRepository repository) {
        this.repository = repository;
    }

    @Override
    public Class<Court> getEntityTypeClass() {
        return Court.class;
    }
}
