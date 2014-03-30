package com.kamhoops.service;


import com.kamhoops.data.domain.Season;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.exception.TestingValidationError;
import com.kamhoops.exceptions.EntityValidationException;
import com.kamhoops.services.SeasonService;
import com.kamhoops.support.BaseTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.time.LocalDate;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SeasonServiceTests extends BaseTest {
    @Autowired
    private SeasonService seasonService;

    @Before
    public void init() {
        dataGenerator.deleteAll();
    }

    @After
    public void cleanData() {
        seasonService.getRepository().deleteAll();
    }

    @Test(expected = TestingValidationError.class)
    public void seasonStartDateCannotBeNull() throws TestingValidationError {
        Season season = new Season();
        season.setEndDate(Date.valueOf(LocalDate.of(2013, 3, 31)));

        validateEntity(season);

        fail("should have thrown TestingValidationError");
    }

    @Test(expected = TestingValidationError.class)
    public void seasonEndDateCannotBeNull() throws TestingValidationError {
        Season season = new Season();
        season.setStartDate(Date.valueOf(LocalDate.of(2012, 9, 1)));

        validateEntity(season);

        fail("should have thrown TestingValidationError");
    }

    @Test(expected = EntityValidationException.class)
    public void shouldThrowEntityValidationExceptionWhenEndDateIsBeforeStartDate() throws EntityValidationException, TestingValidationError, EntityNotFoundException {
        Season season = new Season();

        season.setEndDate(Date.valueOf(LocalDate.of(2012, 9, 1)));
        season.setStartDate(Date.valueOf(LocalDate.of(2013, 3, 31)));

        validateEntity(season);

        seasonService.create(season);
    }

    @Test(expected = EntityValidationException.class)
    public void shouldThrowEntityValidationExceptionWhenStartDateIsWithinAnotherSeason() throws EntityValidationException, TestingValidationError, EntityNotFoundException {
        Season season = new Season();

        season.setStartDate(Date.valueOf(LocalDate.of(2012, 3, 31)));
        season.setEndDate(Date.valueOf(LocalDate.of(2013, 9, 1)));
        seasonService.create(season);

        season = new Season();
        season.setStartDate(Date.valueOf(LocalDate.of(2012, 4, 30)));
        season.setEndDate(Date.valueOf(LocalDate.of(2013, 10, 1)));
        seasonService.create(season);
    }

    @Test(expected = EntityValidationException.class)
    public void shouldThrowEntityValidationExceptionWhenEndDateIsWithinAnotherSeason() throws EntityValidationException, TestingValidationError, EntityNotFoundException {
        Season season = new Season();

        season.setStartDate(Date.valueOf(LocalDate.of(2012, 3, 31)));
        season.setEndDate(Date.valueOf(LocalDate.of(2013, 9, 1)));
        seasonService.create(season);

        season = new Season();
        season.setStartDate(Date.valueOf(LocalDate.of(2012, 2, 28)));
        season.setEndDate(Date.valueOf(LocalDate.of(2013, 8, 1)));
        seasonService.create(season);
    }

    @Test(expected = EntityValidationException.class)
    public void shouldThrowEntityValidationExceptionWhenAnotherSeasonIsMarkedCurrent() throws EntityValidationException, TestingValidationError, EntityNotFoundException {
        Season season = dataGenerator.getTestSeason();
        season.setCurrentSeason(true);
        seasonService.create(season);

        Season badSeason = new Season();
        badSeason.setStartDate(Date.valueOf(LocalDate.of(2014, 2, 28)));
        badSeason.setEndDate(Date.valueOf(LocalDate.of(2015, 8, 1)));
        badSeason.setCurrentSeason(true);
        seasonService.create(badSeason);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCreatingASeasonWithAnId() throws EntityValidationException, EntityNotFoundException {
        Season season = dataGenerator.createTestSeason();

        seasonService.create(season);
    }

    @Test
    public void shouldCreateASeason() throws EntityValidationException, TestingValidationError, EntityNotFoundException {
        Season season = new Season();

        season.setStartDate(Date.valueOf(LocalDate.of(2012, 9, 1)));
        season.setEndDate(Date.valueOf(LocalDate.of(2013, 3, 31)));
        season.setCurrentSeason(true);

        validateEntity(season);

        seasonService.create(season);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenUpdatingANullSeason() throws EntityValidationException, EntityNotFoundException {
        seasonService.update(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenUpdatingASeasonWithANullId() throws EntityValidationException, EntityNotFoundException {
        Season season = dataGenerator.getTestSeason();
        seasonService.update(season);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundExceptionWhenUpdatingASeasonWithAnInvalidId() throws EntityValidationException, EntityNotFoundException {
        Season season = dataGenerator.getTestSeason();
        season.setId(99L);
        seasonService.update(season);
    }

    @Test
    public void shouldUpdateASeason() throws EntityValidationException, EntityNotFoundException {
        Season season = dataGenerator.createTestSeason();

        season.setCurrentSeason(true);

        seasonService.update(season);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenFindByDateIsNull() throws EntityNotFoundException {
        seasonService.findByDate(null);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundExceptionWhenFindByDateIsNotFound() throws EntityNotFoundException {
        seasonService.findByDate(Date.valueOf(LocalDate.now()));
    }

    @Test
    public void shouldFindByDate() throws EntityNotFoundException, EntityValidationException {
        Season season = dataGenerator.createTestSeason();
        season = seasonService.findByDate(season.getStartDate());

        assertThat(season).isNotNull();
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundExceptionWhenThereIsNoCurrentSeason() throws EntityNotFoundException {
        seasonService.findCurrent();
    }

    @Test
    public void shouldFindCurrentSeason() throws EntityNotFoundException, EntityValidationException {
        Season season = dataGenerator.getTestSeason();
        season.setCurrentSeason(true);
        seasonService.create(season);

        seasonService.findCurrent();
    }

    @Test
    public void repositoryShouldHaveAnEntityType() {
        assertTrue(seasonService.getEntityTypeClass() == Season.class);
    }

}
