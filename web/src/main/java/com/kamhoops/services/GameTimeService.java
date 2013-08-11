package com.kamhoops.services;


import com.kamhoops.data.domain.GameTime;
import com.kamhoops.data.repository.GameTimeRepository;
import com.kamhoops.exceptions.EntityValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameTimeService extends AbstractService<GameTimeRepository, GameTime> {

    @Autowired
    GameTimeRepository gameTimeRepository;

    /**
     * Create a GameTime
     *
     * @param gameTime to create
     * @return gameTime created
     * @throws EntityValidationException
     */
    public GameTime create(GameTime gameTime) throws EntityValidationException {
        validateEntity(gameTime);

        return gameTimeRepository.save(gameTime);
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
