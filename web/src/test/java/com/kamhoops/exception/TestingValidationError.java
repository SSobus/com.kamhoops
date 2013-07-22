package com.kamhoops.exception;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

/**
 * Thrown when an error occurs during the testing phase of entity validation
 */
public class TestingValidationError extends Exception {
    private List<ObjectError> errors;

    public TestingValidationError(List<ObjectError> allErrors) {
        this(allErrors, null);
    }

    public TestingValidationError(List<ObjectError> allErrors, Throwable cause) {
        super(cause);
        this.errors = allErrors;
    }

    public TestingValidationError(ObjectError error) {
        this(error, null);
    }

    public TestingValidationError(ObjectError error, Throwable cause) {
        super(cause);

        this.errors = new ArrayList<>();

        if (error != null) {
            errors.add(error);
        }
    }

    public List<ObjectError> getErrors() {
        return errors;
    }

    @Override
    public String getMessage() {
        String msg = null;

        if (errors != null && errors.size() > 0) {
            msg = "Validation of the " + (getEntityType().length() > 0 ? getEntityType() + " " : "") + "entity failed because of the following errors: \n";

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
