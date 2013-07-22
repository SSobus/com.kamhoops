package com.kamhoops.support.json;

import com.kamhoops.data.domain.Users;
import com.kamhoops.data.domain.base.AbstractEntity;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.exception.TestingValidationError;
import com.kamhoops.exceptions.DuplicateEntityException;
import com.kamhoops.exceptions.EntityValidationException;
import com.kamhoops.support.BaseTest;
import org.junit.Test;
import org.springframework.validation.ObjectError;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

/**
 * JSON Object Error tests
 */
public class JsonObjectErrorTests extends BaseTest {

    @Test
    public void shouldBuildAJsonObjectErrorFromAnEntityNotFoundException() {
        JsonObjectError jsonObjectError = JsonObjectError.fromEntityNotFoundException(new EntityNotFoundException(AbstractEntity.class, 1L));

        assertThat(jsonObjectError.getId()).isEqualTo("1");
        assertThat(jsonObjectError.getObjectName()).isEqualTo("AbstractEntity");
        assertThat(jsonObjectError.getFieldName()).isEqualTo("");
        assertThat(jsonObjectError.getErrorType()).isEqualTo(JsonObjectError.ErrorType.NOT_FOUND);
    }

    @Test
    public void shouldBuildAJsonObjectErrorFromADuplicateEntityException() {
        Users user = new Users();
        user.setId(1L);

        DuplicateEntityException duplicateEntityException = new DuplicateEntityException(user, "user", "id");
        JsonObjectError jsonObjectError = JsonObjectError.fromDuplicateEntityException(duplicateEntityException);

        assertThat(jsonObjectError.getId()).isEqualTo("1");
        assertThat(jsonObjectError.getObjectName()).isEqualTo("user");
        assertThat(jsonObjectError.getFieldName()).isEqualTo("id");
        assertThat(jsonObjectError.getErrorType()).isEqualTo(JsonObjectError.ErrorType.DUPLICATION);
    }

    @Test
    public void shouldBuildAJsonObjectErrorFromAEntityValidationError() {
        List<ObjectError> validationErrors = null;

        Users user = new Users();
        user.setId(1L);
        user.setPassword("1234");

        try {
            validateEntity(user);
            fail();
        } catch (TestingValidationError testingValidationError) {
            validationErrors = testingValidationError.getErrors();
        }

        EntityValidationException entityValidationException = new EntityValidationException(validationErrors);
        List<JsonObjectError> jsonObjectError = JsonObjectError.fromEntityValidationException(entityValidationException);

        assertThat(jsonObjectError).hasSize(1);

        JsonObjectError jsonObjectError1 = jsonObjectError.get(0);

        assertThat(jsonObjectError1.getId()).isNull();
        assertThat(jsonObjectError1.getObjectName()).isEqualTo("users");
        assertThat(jsonObjectError1.getFieldName()).isEqualTo("email");
        assertThat(jsonObjectError1.getErrorType()).isEqualTo(JsonObjectError.ErrorType.VALIDATION);
        assertThat(jsonObjectError1.getErrorMessage()).isNotNull();
    }
}
