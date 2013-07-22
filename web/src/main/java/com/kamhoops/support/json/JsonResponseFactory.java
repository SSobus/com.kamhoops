package com.kamhoops.support.json;

import java.util.List;

/**
 * Json Response Factory
 * <p/>
 * Factory to create fluent JSON responses
 */
public class JsonResponseFactory {
    public static <T> AbstractJsonResponse<T> Success(T result) {
        return new JsonSuccessResponse<T>(result);
    }

    public static <T> AbstractJsonResponse<T> Failed(JsonObjectError jsonObjectError) {
        return new JsonFailedResponse<T>(jsonObjectError);
    }

    public static <T> AbstractJsonResponse<T> Failed(List<JsonObjectError> jsonObjectErrors) {
        return new JsonFailedResponse<T>(jsonObjectErrors);
    }
}
