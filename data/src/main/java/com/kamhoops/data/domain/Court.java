package com.kamhoops.data.domain;

import com.kamhoops.data.domain.base.AbstractEntity;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Size;

@Entity
@SequenceGenerator(name = "id_gen", sequenceName = "court_sequence")
public class Court extends AbstractEntity {

    @NotBlank
    @Size(max = 32)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
