package com.kamhoops.services;

import com.kamhoops.data.domain.Player;
import com.kamhoops.data.domain.Team;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.data.repository.TeamRepository;
import com.kamhoops.exceptions.EntityValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TeamService extends AbstractService<TeamRepository, Team> {

    @Autowired
    private PlayerService playerService;

    public Team create(Team team) throws EntityValidationException, EntityNotFoundException {
        preCreateChecks(team);

        repository.save(team);

        return findById(team.getId());
    }

    private void preCreateChecks(Team team) throws EntityValidationException {
        Assert.notNull(team, "Supplied team cannot be null");
        Assert.isNull(team.getId(), "Supplied team cannot have an id");

        validateEntity(team);
    }

    public Team update(Team team) throws EntityNotFoundException, EntityValidationException {
        preUpdateChecksAndMerges(team);

        team = repository.save(team);

        return findById(team.getId());
    }

    public Team preUpdateChecksAndMerges(Team team) throws EntityNotFoundException, EntityValidationException {
        Assert.notNull(team, "Supplied team cannot be null");
        Assert.notNull(team.getId(), "Supplied team id cannot be null");

        validateEntity(team);

        Team existingTeam = findById(team.getId());

        existingTeam.setName(team.getName());

        validateEntity(existingTeam);

        return existingTeam;
    }

    public Team addPlayer(Team team, Player player) throws EntityNotFoundException {
        Assert.notNull(team, "Supplied team cannot be null");
        Assert.notNull(player, "Supplied player cannot be null");

        team = findById(team.getId());
        player = playerService.findById(player.getId());

        team.addPlayer(player);

        return team;
    }

    public Team removePlayer(Team team, Player player) throws EntityNotFoundException {
        Assert.notNull(team, "Supplied team cannot be null");
        Assert.notNull(player, "Supplied player cannot be null");

        team = findById(team.getId());
        player = playerService.findById(player.getId());

        player.removeTeam(team);

        return team;
    }

    @Override
    public TeamRepository getRepository() {
        return repository;
    }

    @Override
    public Class<Team> getEntityTypeClass() {
        return Team.class;
    }
}
