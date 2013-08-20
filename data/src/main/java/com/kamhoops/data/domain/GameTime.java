package com.kamhoops.data.domain;

import com.kamhoops.data.domain.base.AbstractEntity;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Size;

@Entity
@SequenceGenerator(name = "id_gen", sequenceName = "gameTime_sequence")
public class GameTime extends AbstractEntity {

    @NotBlank
    @Size(max = 16)
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
