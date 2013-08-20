package com.kamhoops.service;


import com.kamhoops.data.domain.Player;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.data.repository.PlayerRepository;
import com.kamhoops.exceptions.EntityValidationException;
import com.kamhoops.services.PlayerService;
import com.kamhoops.support.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

public class PlayerServiceTests extends BaseTest {
    @Autowired
    private PlayerService playerService;

    PlayerRepository playerRepository;

    @Before
    public void setup() {
        this.dataGenerator.deleteAll();
        this.playerRepository = playerService.getRepository();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCreatingNull() throws EntityNotFoundException, EntityValidationException {
        playerService.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCreatingWithAnId() throws EntityNotFoundException, EntityValidationException {
        Player player = new Player();
        player.setId(99L);

        playerService.create(player);
    }

    @Test(expected = EntityValidationException.class)
    public void shouldThrowEntityValidationExceptionWhenCreatingWithABlankFirstName() throws EntityNotFoundException, EntityValidationException {
        Player player = new Player();
        player.setFirstName(" ");
        player.setLastName("LastName");

        playerService.create(player);
    }

    @Test(expected = EntityValidationException.class)
    public void shouldThrowEntityValidationExceptionWhenCreatingWithABlankLastName() throws EntityNotFoundException, EntityValidationException {
        Player player = new Player();
        player.setFirstName("FirstName");
        player.setLastName(" ");

        playerService.create(player);
    }

    @Test
    public void shouldCreatePlayer() throws EntityNotFoundException, EntityValidationException {
        Player player = new Player();
        player.setFirstName("FirstName");
        player.setLastName("LastName");

        playerService.create(player);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenUpdatingWithANull() throws EntityValidationException, EntityNotFoundException {
        playerService.update(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenUpdatingWithANullId() throws EntityNotFoundException, EntityValidationException {
        Player player = new Player();
        player.setId(null);

        playerService.update(player);
    }

    @Test(expected = EntityValidationException.class)
    public void shouldThrowEntityValidationExceptionWhenUpdatingWithABlankFirstName() throws EntityNotFoundException, EntityValidationException {
        Player player = new Player();
        player.setFirstName(" ");
        player.setLastName("LastName");

        playerService.update(player);
    }

    @Test(expected = EntityValidationException.class)
    public void shouldThrowEntityValidationExceptionWhenUpdatingWithABlankLastName() throws EntityNotFoundException, EntityValidationException {
        Player player = new Player();
        player.setFirstName("FirstName");
        player.setLastName(" ");

        playerService.update(player);
    }

    //TODO: Test Success Updater

    //TODO: Test Add and Remove Team

    @Test
    public void repositoryShouldHaveAnEntityType() {
        assertTrue(playerService.getEntityTypeClass() == Player.class);
    }
}
