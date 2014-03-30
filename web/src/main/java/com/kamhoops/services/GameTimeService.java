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

    public GameTime create(GameTime gameTime) throws EntityValidationException, EntityNotFoundException {
        preCreateChecks(gameTime);

        gameTime = repository.save(gameTime);

        return findById(gameTime.getId());
    }

    private void preCreateChecks(GameTime gameTime) throws EntityValidationException {
        Assert.notNull(gameTime, "Supplied gameTime cannot be null");
        Assert.isNull(gameTime.getId(), "Supplied gameTime cannot have an Id, are you sure this object should be created");

        validateEntity(gameTime);
    }

    public GameTime update(GameTime gameTime) throws EntityValidationException, EntityNotFoundException {
        gameTime = preUpdateChecksAndMerges(gameTime);

        gameTime = repository.save(gameTime);

        return findById(gameTime.getId());
    }

    private GameTime preUpdateChecksAndMerges(GameTime gameTime) throws EntityValidationException, EntityNotFoundException {
        Assert.notNull(gameTime, "Supplied gameTime cannot be null");
        Assert.notNull(gameTime.getId(), "Supplied gameTime does not have an Id, are you sure this object should be updated");

        validateEntity(gameTime);

        GameTime existingGameTime = findById(gameTime.getId());

        existingGameTime.setTime(gameTime.getTime());

        validateEntity(existingGameTime);

        return existingGameTime;


    }

    @Override
    public GameTimeRepository getRepository() {
        return repository;
    }

    @Override
    public Class<GameTime> getEntityTypeClass() {
        return GameTime.class;
    }
}
