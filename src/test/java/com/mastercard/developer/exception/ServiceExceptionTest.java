package com.mastercard.developer.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.ErrorDetail;
import org.openapitools.client.model.Errors;
import org.openapitools.client.model.ErrorsList;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceExceptionTest {

    private static final String MESSAGE = "The service had an internal exception";
    private static final String SOURCE = "Loyalty-Enrollment";
    private static final String REASON_CODE = "INVALID_FIELD_FORMAT";
    private static final String DESCRIPTION = "Email Address must be in the valid format.";

    @Test
    void testServiceExceptionMessage() {
        ServiceException exception = new ServiceException(MESSAGE);
        assertEquals(MESSAGE, exception.getMessage());
    }

    @Test
    void testServiceExceptionThrowable() {
        try {
            throwException();
        } catch (ServiceException e) {
            Assertions.assertAll(
                    () -> assertEquals(MESSAGE, e.getMessage()),
                    () -> {
                        Errors errors = e.getServiceErrors();
                        assertNotNull(errors);
                        List<ErrorDetail> errorList = errors.getErrors().getError();
                        assertFalse(errorList.isEmpty());
                        errorList.forEach(error -> {
                            assertEquals(SOURCE, error.getSource());
                            assertEquals(REASON_CODE, error.getReasonCode());
                            assertEquals(DESCRIPTION, error.getDescription());
                            Assertions.assertFalse(error::getRecoverable);
                        });
                    }
            );
        }
    }

    private void throwException() throws ServiceException {
        throw new ServiceException(MESSAGE, getCustomError());
    }

    private Errors getCustomError() {
        ErrorDetail error = new ErrorDetail();
        error.source(SOURCE).reasonCode(REASON_CODE).description(DESCRIPTION).recoverable(false);
        ErrorsList errorList = new ErrorsList();
        errorList.addErrorItem(error);
        return new Errors().errors(errorList);
    }
}
