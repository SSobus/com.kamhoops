package com.kamhoops.service;


import com.kamhoops.data.domain.Team;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.data.repository.TeamRepository;
import com.kamhoops.exceptions.EntityValidationException;
import com.kamhoops.services.TeamService;
import com.kamhoops.support.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

public class TeamServiceTests extends BaseTest {
    @Autowired
    private TeamService teamService;

    TeamRepository teamRepository;

    @Before
    public void setup() {
        this.dataGenerator.deleteAll();
        this.teamRepository = teamService.getRepository();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCreatingNull() throws EntityNotFoundException, EntityValidationException {
        teamService.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCreatingWithAnId() throws EntityNotFoundException, EntityValidationException {
        Team team = new Team();
        team.setId(99L);

        teamService.create(team);
    }

    @Test(expected = EntityValidationException.class)
    public void shouldThrowEntityValidationExceptionWhenCreatingWithABlankName() throws EntityNotFoundException, EntityValidationException {
        Team team = new Team();
        team.setName(" ");

        teamService.create(team);
    }

    @Test
    public void shouldCreateATeam() throws EntityNotFoundException, EntityValidationException {
        Team team = new Team();
        team.setName("Name");

        teamService.create(team);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenUpdatingWithANull() throws EntityValidationException, EntityNotFoundException {
        teamService.update(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenUpdatingWithANullId() throws EntityNotFoundException, EntityValidationException {
        Team team = dataGenerator.createTestTeam();
        team.setId(null);

        teamService.update(team);
    }

    @Test(expected = EntityValidationException.class)
    public void shouldThrowEntityValidationExceptionWhenUpdatingWithABlankFirstName() throws EntityNotFoundException, EntityValidationException {
        Team team = dataGenerator.createTestTeam();
        team.setName(" ");

        teamService.update(team);
    }

    @Test
    public void shouldUpdateTeam() throws EntityValidationException, EntityNotFoundException {
        Team team = dataGenerator.createTestTeam();
        team.setName("Team");

        team = teamService.update(team);

        assertTrue(team.getName().equals("Team"));
    }

    //TODO: Test Add and Remove Player

    @Test
    public void repositoryShouldHaveAnEntityType() {
        assertTrue(teamService.getEntityTypeClass() == Team.class);
    }
}
