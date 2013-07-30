package com.kamhoops.service;


import com.kamhoops.data.domain.Season;
import com.kamhoops.data.repository.SeasonRepository;
import com.kamhoops.exception.TestingValidationError;
import com.kamhoops.exceptions.EntityValidationException;
import com.kamhoops.services.SeasonService;
import com.kamhoops.support.BaseTest;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SeasonServiceTests extends BaseTest {
    @Autowired
    SeasonService seasonService;

    private SeasonRepository seasonRepository;

    @Before
    public void init() {
        dataGenerator.deleteAll();
        seasonRepository = seasonService.getRepository();
    }

    @Test(expected = TestingValidationError.class)
    public void seasonStartDateCannotBeNull() throws TestingValidationError {
        Season season = new Season();
        season.setEndDate(new LocalDate(2013, 3, 31).toDate());

        validateEntity(season);

        fail("should have thrown TestingValidationError");
    }

    @Test(expected = TestingValidationError.class)
    public void seasonEndDateCannotBeNull() throws TestingValidationError {
        Season season = new Season();
        season.setStartDate(new LocalDate(2012, 9, 1).toDate());

        validateEntity(season);

        fail("should have thrown TestingValidationError");
    }

    @Test
    public void shouldCreateASeason() throws EntityValidationException, TestingValidationError {
        Season season = new Season();

        season.setStartDate(new LocalDate(2012, 9, 1).toDate());
        season.setEndDate(new LocalDate(2013, 3, 31).toDate());

        validateEntity(season);

        seasonService.save(season);
    }

    @Test
    public void repositoryShouldHaveAnEntityType() {
        assertTrue(seasonService.getEntityTypeClass() == Season.class);
    }

}
