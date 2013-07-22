package com.kamhoops.support.json;

import com.kamhoops.data.domain.base.AbstractEntity;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * JSON Success/Failure tests
 */
public class JsonResponseFactoryTests {

    @Test
    public void aSuccessResponseShouldReturnAnInstanceOfJsonSuccessResponseWithA200HttpStatusCode() {
        AbstractJsonResponse<String> jsonResponse = JsonResponseFactory.Success(".");

        assertThat(jsonResponse).isInstanceOf(JsonSuccessResponse.class);
        assertThat(jsonResponse.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(jsonResponse.getStatus()).isEqualTo(AbstractJsonResponse.Status.SUCCESS);
    }

    @Test
    public void aFailedResponseShouldReturnAnInstanceOfJsonFailedResponseWithA500HttpStatusCode() {
        JsonObjectError jsonObjectError = JsonObjectError.fromEntityNotFoundException(new EntityNotFoundException(AbstractEntity.class, 1L));
        AbstractJsonResponse<String> jsonResponse = JsonResponseFactory.Failed(jsonObjectError);

        assertThat(jsonResponse).isInstanceOf(JsonFailedResponse.class);
        assertThat(jsonResponse.getHttpStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(jsonResponse.getStatus()).isEqualTo(AbstractJsonResponse.Status.FAIL);
    }

    @Test
    public void theFactoryCanCreateAFailResponseFromAListOfJsonObjectErrors() {
        List<JsonObjectError> jsonObjectErrors = new ArrayList<>();


        jsonObjectErrors.add(JsonObjectError.fromEntityNotFoundException(new EntityNotFoundException(AbstractEntity.class, 1L)));
        jsonObjectErrors.add(JsonObjectError.fromEntityNotFoundException(new EntityNotFoundException(AbstractEntity.class, 2L)));

        AbstractJsonResponse<String> jsonResponse = JsonResponseFactory.Failed(jsonObjectErrors);

        assertThat(jsonResponse).isInstanceOf(JsonFailedResponse.class);
        assertThat(jsonResponse.getHttpStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        assertThat(((JsonFailedResponse) jsonResponse).getErrors()).hasSize(2);
    }

    @Test
    public void theResponseThatTheFactoryCreatesShouldFillTheResponseResult() {
        AbstractJsonResponse<String> jsonResponse = JsonResponseFactory.Success(".");

        assertThat(jsonResponse).isInstanceOf(JsonSuccessResponse.class);
        assertThat(jsonResponse.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(jsonResponse.getStatus()).isEqualTo(AbstractJsonResponse.Status.SUCCESS);
        assertThat(jsonResponse.getResult()).isEqualTo(".");
    }

    @Test
    public void anyJsonResponseShouldBeConvertableIntoAResponseEntity() {
        AbstractJsonResponse<String> jsonResponse = JsonResponseFactory.Success(".");
        ResponseEntity<AbstractJsonResponse<String>> responseEntity = jsonResponse.asResponseEntity();

        assertThat(responseEntity.getStatusCode()).isEqualTo(jsonResponse.getHttpStatus());
    }
}
