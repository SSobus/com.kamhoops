package com.kamhoops.support.json;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * JSON Response
 * <p/>
 * Simple wrapper for a JSON response of type T to indicate success/failure
 */
public abstract class AbstractJsonResponse<T> {

    protected enum Status {
        SUCCESS,
        PARTIAL_SUCCESS,
        FAIL
    }

    private Status status = null;
    private T result = null;

    public AbstractJsonResponse(Status status, T result) {
        this.status = status;
        this.result = result;
    }

    public Status getStatus() {
        return status;
    }

    public T getResult() {
        return result;
    }

    protected abstract HttpStatus getHttpStatus();

    public ResponseEntity<AbstractJsonResponse<T>> asResponseEntity() {
        return new ResponseEntity<>(this, getHttpStatus());
    }
}
