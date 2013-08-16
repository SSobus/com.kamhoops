package com.kamhoops.data.domain;

import com.kamhoops.data.domain.base.AbstractEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@SequenceGenerator(name = "id_gen", sequenceName = "team_sequence")
public class Team extends AbstractEntity {

    private String name;

    @ManyToMany
    @JoinTable(name = "TEAM_PLAYER",
            joinColumns = {@JoinColumn(name = "TEAM")},
            inverseJoinColumns = {@JoinColumn(name = "PLAYER")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Player> player;

}
