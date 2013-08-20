package com.kamhoops.services;

import com.kamhoops.data.domain.Player;
import com.kamhoops.data.domain.Team;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.data.repository.PlayerRepository;
import com.kamhoops.exceptions.EntityValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class PlayerService extends AbstractService<PlayerRepository, Player> {

    @Autowired
    private TeamService teamService;

    public Player create(Player player) throws EntityValidationException, EntityNotFoundException {
        preCreateChecks(player);

        repository.save(player);

        return findById(player.getId());
    }

    private void preCreateChecks(Player player) throws EntityValidationException {
        Assert.notNull(player, "Supplied player cannot be null");
        Assert.isNull(player.getId(), "Supplied player cannot have an id");

        validateEntity(player);
    }

    public Player update(Player player) throws EntityNotFoundException, EntityValidationException {
        preUpdateChecksAndMerges(player);

        player = repository.save(player);

        return findById(player.getId());
    }

    public Player preUpdateChecksAndMerges(Player player) throws EntityNotFoundException, EntityValidationException {
        Assert.notNull(player, "Supplied player cannot be null");
        Assert.notNull(player.getId(), "Supplied player id cannot be null");

        validateEntity(player);

        Player existingPlayer = findById(player.getId());

        existingPlayer.setFirstName(player.getFirstName());
        existingPlayer.setLastName(player.getLastName());

        validateEntity(existingPlayer);

        return existingPlayer;
    }

    public Player addTeam(Player player, Team team) throws EntityNotFoundException {
        Assert.notNull(player, "Supplied player cannot be null");
        Assert.notNull(team, "Supplied team cannot be null");

        player = findById(player.getId());
        team = teamService.findById(team.getId());

        player.addTeam(team);

        return player;
    }

    public Player removeTeam(Player player, Team team) throws EntityNotFoundException {
        Assert.notNull(player, "Supplied player cannot be null");
        Assert.notNull(team, "Supplied team cannot be null");

        player = findById(player.getId());
        team = teamService.findById(team.getId());

        player.removeTeam(team);

        return player;
    }

    @Override
    public PlayerRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(PlayerRepository playerRepository) {
        repository = playerRepository;
    }

    @Override
    public Class<Player> getEntityTypeClass() {
        return Player.class;
    }
}
