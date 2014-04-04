package com.kamhoops.services;

import com.kamhoops.data.domain.Calendar;
import com.kamhoops.data.domain.Game;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.data.repository.CalendarRepository;
import com.kamhoops.exceptions.EntityValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CalendarService extends AbstractService<CalendarRepository, Calendar> {

    @Autowired
    private GameService gameService;

    public Calendar create(Calendar calendar) throws EntityValidationException, EntityNotFoundException {
        preCreateChecks(calendar);

        calendar = repository.save(calendar);

        return findById(calendar.getId());
    }

    private void preCreateChecks(Calendar calendar) throws EntityValidationException {
        Assert.notNull(calendar, "Supplied calendar cannot be null");
        Assert.isNull(calendar.getId(), "Supplied calendar cannot have an Id, are you sure this object should be created");

        validateEntity(calendar);
    }

    public Calendar update(Calendar calendar) throws EntityValidationException, EntityNotFoundException {
        calendar = preUpdateChecksAndMerges(calendar);

        calendar = repository.save(calendar);

        return findById(calendar.getId());
    }

    private Calendar preUpdateChecksAndMerges(Calendar calendar) throws EntityValidationException, EntityNotFoundException {
        Assert.notNull(calendar, "Supplied calendar cannot be null");
        Assert.notNull(calendar.getId(), "Supplied calendar does not have an Id, are you sure this object should be updated");

        validateEntity(calendar);

        Calendar existingCalendar = findById(calendar.getId());

        //merge changes into existing entity
        existingCalendar.setGameDay(calendar.getGameDay());

        validateEntity(existingCalendar);

        return existingCalendar;
    }

    public Calendar addGame(Calendar calendar, Game game) throws EntityNotFoundException {
        Assert.notNull(calendar, "Supplied calendar cannot be null");
        Assert.notNull(game, "Supplied game cannot be null");

        calendar = findById(calendar.getId());
        game = gameService.findById(game.getId());

        calendar.addGame(game);

        return calendar;
    }

    public Calendar removeGame(Calendar calendar, Game game) throws EntityNotFoundException {
        Assert.notNull(calendar, "Supplied calendar cannot be null");
        Assert.notNull(game, "Supplied game cannot be null");

        calendar = findById(calendar.getId());
        game = gameService.findById(game.getId());

        calendar.removeGame(game);

        return calendar;
    }

    @Override
    public CalendarRepository getRepository() {
        return repository;
    }

    @Override
    public Class<Calendar> getEntityTypeClass() {
        return Calendar.class;
    }
}
