package com.kamhoops.support.json;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Json Failed Response
 * <p/>
 * Used to send error information to the client about the cause of the failure
 */
public class JsonFailedResponse<T> extends AbstractJsonResponse<T> {

    private List<JsonObjectError> errors;

    public JsonFailedResponse(JsonObjectError error) {
        super(Status.FAIL, null);
        assert (error != null);

        this.errors = new ArrayList<>();
        this.errors.add(error);
    }

    public JsonFailedResponse(List<JsonObjectError> errors) {
        super(Status.FAIL, null);
        assert (errors != null);
        assert (errors.size() > 0);

        this.errors = errors;
    }

    public List<JsonObjectError> getErrors() {
        return errors;
    }

    @Override
    protected HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
