package com.kamhoops.data.domain;

import com.kamhoops.data.domain.base.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;


@Entity
@SequenceGenerator(name = "id_gen", sequenceName = "game_sequence")
public class Game extends AbstractEntity {

    @ManyToOne
    private Calendar calendar;

    private Court court;

    private GameTime gameTime;

    private Team home;

    private Team away;

    private Integer homeScore;

    private Integer awayScore;

}
