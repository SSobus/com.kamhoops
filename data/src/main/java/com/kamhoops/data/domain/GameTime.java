package com.kamhoops.data.domain;

import com.kamhoops.data.domain.base.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "id_gen", sequenceName = "gameTime_sequence")
public class GameTime extends AbstractEntity {

    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
