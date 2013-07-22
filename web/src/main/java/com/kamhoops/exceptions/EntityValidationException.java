package com.kamhoops.exceptions;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

/**
 * Thrown when an error occurs during the creation of an entry
 */
public class EntityValidationException extends Exception {
    private List<ObjectError> errors;

    public EntityValidationException(List<ObjectError> allErrors) {
        this(allErrors, null);
    }

    public EntityValidationException(List<ObjectError> allErrors, Throwable cause) {
        super(cause);
        assert (allErrors != null);

        this.errors = allErrors;
    }

    public EntityValidationException(ObjectError error) {
        this(error, null);
    }

    public EntityValidationException(ObjectError error, Throwable cause) {
        super(cause);
        assert (error != null);

        this.errors = new ArrayList<>();
        errors.add(error);
    }

    public List<ObjectError> getErrors() {
        return errors;
    }

    @Override
    public String getMessage() {
        String msg = null;

        if (errors != null && errors.size() > 0) {
            msg = "Could not validate " + (getEntityType().length() > 0 ? getEntityType() + " " : "") + "entity because of the following errors: \n";

            for (ObjectError e : errors) {
                msg += " - " + (e instanceof FieldError ? e.toString() : e.getDefaultMessage()) + "\n";
            }
        }

        return (msg != null ? msg : super.getMessage());
    }

    public String getEntityType() {
        return "";
    }
}
