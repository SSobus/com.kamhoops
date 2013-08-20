package com.kamhoops.service;

import com.kamhoops.data.domain.Court;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.data.repository.CourtRepository;
import com.kamhoops.exception.TestingValidationError;
import com.kamhoops.exceptions.EntityValidationException;
import com.kamhoops.services.CourtService;
import com.kamhoops.support.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

public class CourtServiceTests extends BaseTest {

    @Autowired
    CourtService courtService;

    private CourtRepository courtRepository;

    @Before
    public void init() {
        dataGenerator.deleteAll();
        courtRepository = courtService.getRepository();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCreatingNull() throws EntityValidationException, EntityNotFoundException {
        courtService.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCreatingWithAnId() throws EntityValidationException, EntityNotFoundException {
        Court court = new Court();
        court.setId(99L);

        courtService.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCreatingWithABlankName() throws EntityValidationException, EntityNotFoundException {
        Court court = new Court();
        court.setName(" ");

        courtService.create(null);
    }

    @Test
    public void shouldSaveACourt() throws EntityValidationException, TestingValidationError, EntityNotFoundException {
        Court court = new Court();

        court.setName("Champ");

        validateEntity(court);

        courtService.create(court);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenUpdatingNull() throws EntityValidationException, EntityNotFoundException {
        courtService.update(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenUpdatingWithANullId() throws EntityValidationException, EntityNotFoundException {
        Court court = dataGenerator.createTestCourt();
        court.setId(null);

        courtService.update(court);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowIllegalArgumentExceptionWhenUpdatingWithAnInvalidId() throws EntityValidationException, EntityNotFoundException {
        Court court = dataGenerator.createTestCourt();
        court.setId(0L);

        courtService.update(court);
    }

    @Test(expected = EntityValidationException.class)
    public void shouldThrowEntityValidationExceptionWhenUpdatingWithABlankTime() throws EntityValidationException, EntityNotFoundException {
        Court court = dataGenerator.createTestCourt();
        court.setName(" ");

        courtService.update(court);
    }

    @Test
    public void shouldUpdateCourt() throws EntityValidationException, EntityNotFoundException {
        Court court = dataGenerator.createTestCourt();
        court.setName("Mid");

        courtService.update(court);
    }

    @Test
    public void repositoryShouldHaveAnEntityType() {
        assertTrue(courtService.getEntityTypeClass() == Court.class);
    }
}
