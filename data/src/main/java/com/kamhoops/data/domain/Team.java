package com.kamhoops.data.domain;

import com.kamhoops.data.domain.base.AbstractEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = "id_gen", sequenceName = "team_sequence")
public class Team extends AbstractEntity {

    @NotBlank
    @Size(max = 32)
    private String name;

    @ManyToMany
    @JoinTable(name = "TEAM_PLAYER",
            joinColumns = {@JoinColumn(name = "TEAM")},
            inverseJoinColumns = {@JoinColumn(name = "PLAYER")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Player> players;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        if (players == null) {
            players = new ArrayList<>();
        }

        if (!players.contains(player)) {
            players.add(player);
        }
    }

    public void removePlayer(Player player) {
        if (players != null && players.contains(player)) {
            players.remove(player);
        }
    }
}
