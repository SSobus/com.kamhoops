package com.kamhoops.support.json;

import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.exceptions.DuplicateEntityException;
import com.kamhoops.exceptions.EntityValidationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

/**
 * Json Object Error
 * <p/>
 * Simple wrapper to encapsulate error information
 */
public class JsonObjectError {
    public static JsonObjectError fromDuplicateEntityException(DuplicateEntityException exception) {
        return new JsonObjectError(exception.getEntity().getId().toString(), exception.getEntityName(), exception.getDuplicationOnFieldName(), ErrorType.DUPLICATION);
    }

    public static List<JsonObjectError> fromEntityValidationException(EntityValidationException exception) {
        List<JsonObjectError> jsonObjectErrors = new ArrayList<>();
        List<ObjectError> errors = exception.getErrors();

        for (ObjectError error : errors) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                jsonObjectErrors.add(new JsonObjectError(null, fieldError.getObjectName(), fieldError.getField(), ErrorType.VALIDATION, fieldError.getDefaultMessage()));
            }
        }


        return jsonObjectErrors;
    }

    public static JsonObjectError fromEntityNotFoundException(EntityNotFoundException exception) {
        return new JsonObjectError(exception.getEntityId(), exception.getEntityName(), "", ErrorType.NOT_FOUND);
    }

    protected enum ErrorType {
        DUPLICATION,
        VALIDATION,
        NOT_FOUND
    }

    private String id;
    private String objectName;
    private String fieldName;
    private String errorMessage;
    private ErrorType errorType;

    private JsonObjectError(String id, String objectName, String fieldName, ErrorType errorType) {
        this.id = id;
        this.objectName = objectName;
        this.fieldName = fieldName;
        this.errorType = errorType;
    }

    private JsonObjectError(String id, String objectName, String fieldName, ErrorType errorType, String errorMessage) {
        this.id = id;
        this.objectName = objectName;
        this.fieldName = fieldName;
        this.errorType = errorType;
        this.errorMessage = errorMessage;
    }

    public String getId() {
        return id;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
