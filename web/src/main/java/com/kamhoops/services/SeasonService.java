package com.kamhoops.services;

import com.kamhoops.data.domain.QSeason;
import com.kamhoops.data.domain.Season;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.data.repository.SeasonRepository;
import com.kamhoops.exceptions.EntityValidationException;
import com.mysema.query.types.expr.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.FieldError;

import java.util.Date;

@Service
public class SeasonService extends AbstractService<SeasonRepository, Season> {

    public Season create(Season season) throws EntityValidationException {
        preCreateChecks(season);

        return repository.save(season);
    }

    private void preCreateChecks(Season season) throws EntityValidationException {
        Assert.notNull(season, "Cannot create null season");
        Assert.isNull(season.getId(), "Cannot create object with a null id");
        Assert.notNull(season.getStartDate(), "Season cannot have a null startDate");
        Assert.notNull(season.getEndDate(), "Season cannot have a null endDate");

        //check to see if Season End Date is before Season Start Date
        if (season.getStartDate().after(season.getEndDate())) {
            throw new EntityValidationException(new FieldError("season", "startDate", "StartDate cannot be after EndDate"));
        }

        checkIfDatesOverlapWithAnotherStartDate(season);
        checkIfDatesOverlapWithAnotherEndDate(season);

        if (season.getCurrentSeason()) {
            checkIfAnotherSeasonMarkedAsCurrent(season);
        }

        validateEntity(season);
    }

    public Season update(Season modifiedSeason) throws EntityNotFoundException, EntityValidationException {
        preUpdateChecksAndMerge(modifiedSeason);

        return repository.save(modifiedSeason);
    }

    private void preUpdateChecksAndMerge(Season modifiedSeason) throws EntityValidationException, EntityNotFoundException {
        Assert.notNull(modifiedSeason, "Cannot update null season");
        Assert.notNull(modifiedSeason.getId(), "Cannot update object with a null id");
        Assert.notNull(modifiedSeason.getStartDate(), "Season cannot have a null startDate");
        Assert.notNull(modifiedSeason.getEndDate(), "Season cannot have a null endDate");

        //forces a check to see if season exists
        Season existingSeason = findById(modifiedSeason.getId());

        //check dates for overlapping
        if (!existingSeason.getStartDate().equals(modifiedSeason.getStartDate())) {
            checkIfDatesOverlapWithAnotherStartDate(modifiedSeason);
        }

        if (!existingSeason.getEndDate().equals(modifiedSeason.getEndDate())) {
            checkIfDatesOverlapWithAnotherEndDate(modifiedSeason);
        }

        if (modifiedSeason.getCurrentSeason()) {
            checkIfAnotherSeasonMarkedAsCurrent(modifiedSeason);
        }

        validateEntity(modifiedSeason);

    }

    private void checkIfDatesOverlapWithAnotherStartDate(Season season) throws EntityValidationException {
        BooleanExpression expression = QSeason.season.startDate.between(season.getStartDate(), season.getEndDate());

        //check if id exists in order to filter out current season
        if (season.getId() != null) {
            expression = expression.and(QSeason.season.id.ne(season.getId()));
        }

        if (repository.findAll(expression).iterator().hasNext()) {
            throw new EntityValidationException(new FieldError("season", "startDate", "Dates Conflict With Another Seasons StartDate"));
        }
    }

    private void checkIfDatesOverlapWithAnotherEndDate(Season season) throws EntityValidationException {
        BooleanExpression expression = QSeason.season.endDate.between(season.getStartDate(), season.getEndDate());

        //check if id exists in order to filter out current season
        if (season.getId() != null) {
            expression = expression.and(QSeason.season.id.ne(season.getId()));
        }

        if (repository.findAll(expression).iterator().hasNext()) {
            throw new EntityValidationException(new FieldError("season", "endDate", "Dates Conflict With Another Seasons EndDate"));
        }
    }

    private void checkIfAnotherSeasonMarkedAsCurrent(Season current) throws EntityValidationException {
        Season season;
        BooleanExpression expression = QSeason.season.currentSeason.eq(true);

        //check if id exists in order to filter out current season
        if (current.getId() != null) {
            expression = expression.and(QSeason.season.id.ne(current.getId()));
        }

        season = repository.findOne(expression);

        if (season != null) {
            throw new EntityValidationException(new FieldError("season", "current", "A current season already exists"));
        }
    }

    public Season findByDate(Date date) throws EntityNotFoundException {
        Assert.notNull(date, "date supplied cannot be null");

        //find season with start and end dates around date passed in
        BooleanExpression expression = QSeason.season.startDate.loe(date);
        expression.and(QSeason.season.endDate.goe(date));

        Season season = repository.findOne(expression);

        if (season == null)
            throw new EntityNotFoundException(Season.class, "date");

        return season;
    }

    public Season findCurrent() throws EntityNotFoundException {
        BooleanExpression expression = QSeason.season.currentSeason.eq(true);
        Season season = repository.findOne(expression);

        if (season == null)
            throw new EntityNotFoundException(Season.class, "no current season found");

        return season;
    }

    @Override
    public SeasonRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(SeasonRepository repository) {
        this.repository = repository;
    }

    @Override
    public Class<Season> getEntityTypeClass() {
        return Season.class;
    }
}
