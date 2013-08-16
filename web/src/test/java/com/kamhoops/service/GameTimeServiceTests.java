package com.kamhoops.service;

import com.kamhoops.data.domain.GameTime;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.data.repository.GameTimeRepository;
import com.kamhoops.exception.TestingValidationError;
import com.kamhoops.exceptions.EntityValidationException;
import com.kamhoops.services.GameTimeService;
import com.kamhoops.support.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

public class GameTimeServiceTests extends BaseTest {

    @Autowired
    GameTimeService gameTimeService;

    private GameTimeRepository gameTimeRepository;

    @Before
    public void init() {
        dataGenerator.deleteAll();
        gameTimeRepository = gameTimeService.getRepository();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCreatingNull() throws EntityValidationException {
        gameTimeService.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCreatingWithAnId() throws EntityValidationException {
        GameTime gameTime = new GameTime();
        gameTime.setId(99L);

        gameTimeService.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCreatingWithABlankTime() throws EntityValidationException {
        GameTime gameTime = new GameTime();
        gameTime.setTime(" ");

        gameTimeService.create(null);
    }

    @Test
    public void shouldSaveAGameTime() throws EntityValidationException, TestingValidationError {
        GameTime gameTime = new GameTime();

        gameTime.setTime("7:00");

        validateEntity(gameTime);

        gameTimeService.create(gameTime);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenUpdatingNull() throws EntityValidationException, EntityNotFoundException {
        gameTimeService.update(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenUpdatingWithANullId() throws EntityValidationException, EntityNotFoundException {
        GameTime gameTime = dataGenerator.createTestGameTime();
        gameTime.setId(null);

        gameTimeService.update(gameTime);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowIllegalArgumentExceptionWhenUpdatingWithAnInvalidId() throws EntityValidationException, EntityNotFoundException {
        GameTime gameTime = dataGenerator.createTestGameTime();
        gameTime.setId(0L);

        gameTimeService.update(gameTime);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenUpdatingWithABlankTime() throws EntityValidationException, EntityNotFoundException {
        GameTime gameTime = dataGenerator.createTestGameTime();
        gameTime.setTime(" ");

        gameTimeService.update(gameTime);
    }

    @Test
    public void shouldUpdateGameTime() throws EntityValidationException, EntityNotFoundException {
        GameTime gameTime = dataGenerator.createTestGameTime();
        gameTime.setTime("9:99");

        gameTimeService.update(gameTime);
    }

    @Test
    public void repositoryShouldHaveAnEntityType() {
        assertTrue(gameTimeService.getEntityTypeClass() == GameTime.class);
    }
}
