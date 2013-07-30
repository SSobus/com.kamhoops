package com.kamhoops.data.domain;

import com.kamhoops.data.domain.base.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import java.sql.Time;

@Entity
@SequenceGenerator(name = "id_gen", sequenceName = "gameTime_sequence")
public class GameTime extends AbstractEntity {

    private Time time;

}
