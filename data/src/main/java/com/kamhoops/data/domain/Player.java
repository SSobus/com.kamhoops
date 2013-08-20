package com.kamhoops.data.domain;

import com.kamhoops.data.domain.base.AbstractEntity;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = "id_gen", sequenceName = "player_sequence")
public class Player extends AbstractEntity {

    @NotBlank
    @Size(max = 16)
    private String firstName;

    @NotBlank
    @Size(max = 16)
    private String lastName;

    @ManyToMany(mappedBy = "players")
    private List<Team> teams;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonIgnore
    public List<Team> getTeams() {
        return teams;
    }

    @JsonProperty
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public void addTeam(Team team) {
        if (teams == null) {
            teams = new ArrayList<>();
        }

        if (!teams.contains(team)) {
            teams.add(team);
        }
    }

    public void removeTeam(Team team) {
        if (teams != null && teams.contains(team)) {
            teams.remove(team);
        }
    }
}
