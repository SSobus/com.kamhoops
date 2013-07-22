package com.kamhoops.controllers;

import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.exceptions.DuplicateEntityException;
import com.kamhoops.exceptions.EntityValidationException;
import com.kamhoops.support.json.AbstractJsonResponse;
import com.kamhoops.support.json.JsonObjectError;
import com.kamhoops.support.json.JsonResponseFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global Entity Controller Exception Handler
 * <p/>
 * This controller provides handling for the common service exceptions and it applied to all controllers.
 * <p/>
 * Individual controllers may override if necessary.
 */
@ControllerAdvice
public class GlobalEntityControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<AbstractJsonResponse<Object>> handleEntityNotFoundException(EntityNotFoundException ex) {
        return JsonResponseFactory.Failed(JsonObjectError.fromEntityNotFoundException(ex)).asResponseEntity();
    }

    @ExceptionHandler
    public ResponseEntity<AbstractJsonResponse<Object>> handleDuplicateEntityException(DuplicateEntityException ex) {
        return JsonResponseFactory.Failed(JsonObjectError.fromDuplicateEntityException(ex)).asResponseEntity();
    }

    @ExceptionHandler
    public ResponseEntity<AbstractJsonResponse<Object>> handleEntityValidationException(EntityValidationException ex) {
        return JsonResponseFactory.Failed(JsonObjectError.fromEntityValidationException(ex)).asResponseEntity();
    }
}
