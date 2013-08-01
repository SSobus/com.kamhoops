package com.kamhoops.service;

import com.kamhoops.data.domain.GameTime;
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

    @Test
    public void shouldSaveAGameTime() throws EntityValidationException, TestingValidationError {
        GameTime gameTime = new GameTime();

        gameTime.setTime("7:00");

        validateEntity(gameTime);

        gameTimeService.save(gameTime);
    }

    @Test
    public void repositoryShouldHaveAnEntityType() {
        assertTrue(gameTimeService.getEntityTypeClass() == GameTime.class);
    }
}
