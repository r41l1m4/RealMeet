package com.ironia.realmeet.exception;

import com.ironia.realmeet.validator.ValidationError;
import com.ironia.realmeet.validator.ValidationErrors;

public class InvalidRequestException extends RuntimeException {
    private final ValidationErrors validationErrors;

    public InvalidRequestException(ValidationErrors validationErrors) {
        super(validationErrors.toString());
        this.validationErrors = validationErrors;
    }

    public InvalidRequestException(ValidationError validationError) {
        this(new ValidationErrors().add(validationError));
    }

    public ValidationErrors getValidationErrors() {
        return validationErrors;
    }
}
