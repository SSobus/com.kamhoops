package com.kamhoops.data.domain;


import com.kamhoops.data.domain.base.AbstractEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import java.util.List;

@Entity
@SequenceGenerator(name = "id_gen", sequenceName = "schedule_sequence")
public class Calendar extends AbstractEntity {

    private Date gameDay;

    @OneToMany(mappedBy = "calendar")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Game> games;

    @ManyToOne
    private Season season;

}
