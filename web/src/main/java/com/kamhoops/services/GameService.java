package com.kamhoops.services;

import com.kamhoops.data.domain.Game;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.data.repository.GameRepository;
import com.kamhoops.exceptions.EntityValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class GameService extends AbstractService<GameRepository, Game> {

    @Autowired
    private CalendarService calendarService;

    public Game create(Game game) throws EntityValidationException, EntityNotFoundException {
        preCreateChecks(game);

        game = repository.save(game);

        return findById(game.getId());
    }

    private void preCreateChecks(Game game) throws EntityValidationException {
        Assert.notNull(game, "Supplied game cannot be null");
        Assert.isNull(game.getId(), "Supplied game cannot have an Id, are you sure this object should be created");

        validateEntity(game);
    }

    public Game update(Game game) throws EntityValidationException, EntityNotFoundException {
        game = preUpdateChecksAndMerges(game);

        game = repository.save(game);

        return findById(game.getId());
    }

    private Game preUpdateChecksAndMerges(Game game) throws EntityValidationException, EntityNotFoundException {
        Assert.notNull(game, "Supplied game cannot be null");
        Assert.notNull(game.getId(), "Supplied game does not have an Id, are you sure this object should be updated");

        validateEntity(game);

        Game existingGame = findById(game.getId());

        //merge changes into existing entity
        existingGame.setHome(game.getHome());
        existingGame.setAway(game.getAway());
        existingGame.setHomeScore(game.getHomeScore());
        existingGame.setAwayScore(game.getAwayScore());
        existingGame.setCourt(game.getCourt());
        existingGame.setGameTime(game.getGameTime());

        validateEntity(existingGame);

        return existingGame;
    }

    @Override
    public GameRepository getRepository() {
        return repository;
    }

    @Override
    public Class<Game> getEntityTypeClass() {
        return Game.class;
    }
}
