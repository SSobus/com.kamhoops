package com.kamhoops.data.domain;

import com.kamhoops.data.domain.base.AbstractEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import java.util.List;

@Entity
@SequenceGenerator(name = "id_gen", sequenceName = "season_sequence")
public class Season extends AbstractEntity {

    private Date startDate;

    private Date endDate;

    private Boolean currentSeason;

    @OneToMany(mappedBy = "season")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Calendar> calendar;
}
