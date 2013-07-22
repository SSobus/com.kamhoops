package com.kamhoops.support.json;

import org.springframework.http.HttpStatus;

/**
 * Json Success Response
 * <p/>
 * Used to indicate a success to the client along with some additional information (T)
 */
public class JsonSuccessResponse<T> extends AbstractJsonResponse<T> {
    public JsonSuccessResponse(T result) {
        super(Status.SUCCESS, result);
    }

    @Override
    protected HttpStatus getHttpStatus() {
        return HttpStatus.OK;
    }
}
