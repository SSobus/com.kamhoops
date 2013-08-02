package com.kamhoops.data.domain;

import com.kamhoops.data.domain.base.AbstractEntity;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

public class News extends AbstractEntity {

    @NotBlank
    @Size(max = 64)
    private String title;

    @NotBlank
    @Size(max = 1024)
    private String content;

}
