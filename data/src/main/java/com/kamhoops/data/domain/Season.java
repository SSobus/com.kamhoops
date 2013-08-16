package com.kamhoops.data.domain;

import com.kamhoops.data.domain.base.AbstractEntity;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@SequenceGenerator(name = "id_gen", sequenceName = "season_sequence")
public class Season extends AbstractEntity {

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    private Boolean currentSeason = Boolean.FALSE;

    @OneToMany(mappedBy = "season")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Calendar> calendar;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(Boolean currentSeason) {
        this.currentSeason = currentSeason;
    }

    @JsonIgnore
    public List<Calendar> getCalendar() {
        return calendar;
    }

    @JsonProperty
    public void setCalendar(List<Calendar> calendar) {
        this.calendar = calendar;
    }

    public void addCalendar(Calendar calendar) {
        if (this.calendar == null) {
            this.calendar = new ArrayList<>();
        }

        calendar.setSeason(this);

        if (!this.calendar.contains(calendar)) {
            this.calendar.add(calendar);
        }
    }

    public void removeCalendar(Calendar calendar) {
        if (this.calendar == null) {
            return;
        }

        calendar.setSeason(null);

        if (this.calendar.contains(calendar)) {
            this.calendar.remove(calendar);
        }
    }
}
