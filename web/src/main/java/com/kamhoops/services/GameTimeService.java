package com.kamhoops.services;


import com.kamhoops.data.domain.GameTime;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.data.repository.GameTimeRepository;
import com.kamhoops.exceptions.EntityValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class GameTimeService extends AbstractService<GameTimeRepository, GameTime> {

    public GameTime create(GameTime gameTime) throws EntityValidationException {
        preCreateChecks(gameTime);

        return repository.save(gameTime);
    }

    private void preCreateChecks(GameTime gameTime) throws EntityValidationException {
        Assert.notNull(gameTime, "Supplied gameTime cannot be null");
        Assert.isNull(gameTime.getId(), "Supplied gameTime cannot have an Id, are you sure this object should be created");
        Assert.hasText(gameTime.getTime(), "Supplied time cannot be null or blank");

        validateEntity(gameTime);
    }

    public GameTime update(GameTime gameTime) throws EntityValidationException, EntityNotFoundException {
        preUpdateChecksAndMerges(gameTime);

        return repository.save(gameTime);
    }

    private void preUpdateChecksAndMerges(GameTime gameTime) throws EntityValidationException, EntityNotFoundException {
        Assert.notNull(gameTime, "Supplied gameTime cannot be null");
        Assert.notNull(gameTime.getId(), "Supplied gameTime does not have an Id, are you sure this object should be updated");
        Assert.hasText(gameTime.getTime(), "Supplied time cannot be null or blank");

        //forces a check to see if the gameTime exists
        findById(gameTime.getId());

        validateEntity(gameTime);
    }

    @Override
    public GameTimeRepository getRepository() {
        return repository;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Autowired
    public void setRepository(GameTimeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Class<GameTime> getEntityTypeClass() {
        return GameTime.class;
    }
}
