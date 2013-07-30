package com.kamhoops.data.domain;

import com.kamhoops.data.domain.base.AbstractEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.List;

@Entity
@SequenceGenerator(name = "id_gen", sequenceName = "team_sequence")
public class Team extends AbstractEntity {

    private String name;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Player> player;

}
