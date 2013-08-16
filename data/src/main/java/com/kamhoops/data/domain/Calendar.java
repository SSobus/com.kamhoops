package com.kamhoops.data.domain;


import com.kamhoops.data.domain.base.AbstractEntity;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@SequenceGenerator(name = "id_gen", sequenceName = "schedule_sequence")
public class Calendar extends AbstractEntity {

    private Date gameDay;

    @OneToMany(mappedBy = "calendar")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Game> games;

    public Date getGameDay() {
        return gameDay;
    }

    public void setGameDay(Date gameDay) {
        this.gameDay = gameDay;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    @ManyToOne
    private Season season;


    @JsonIgnore
    public List<Game> getGames() {
        return this.games;
    }

    @JsonProperty
    public void setGames(List<Game> games) {
        this.games = games;
    }

    public void addGame(Game game) {
        if (this.games == null) {
            this.games = new ArrayList<>();
        }

        game.setCalendar(this);

        if (!this.games.contains(game)) {
            this.games.add(game);
        }
    }

    public void removeGame(Game game) {
        if (this.games == null) {
            return;
        }

        game.setCalendar(null);

        if (this.games.contains(game)) {
            this.games.remove(game);
        }
    }

}
