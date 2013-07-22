package com.kamhoops.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

@Component
public class EntityValidator {

    @Autowired
    private Validator validator;

    public BindingResult validate(Object entity) {
        DataBinder binder = new DataBinder(entity, entity.getClass().getSimpleName().toLowerCase());
        binder.setValidator(validator);
        binder.validate();

        return binder.getBindingResult();
    }

}
