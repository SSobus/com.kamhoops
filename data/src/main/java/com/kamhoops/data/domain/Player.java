package com.kamhoops.data.domain;

import com.kamhoops.data.domain.base.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "id_gen", sequenceName = "player_sequence")
public class Player extends AbstractEntity {

    private String firstName;

    private String lastName;

}
