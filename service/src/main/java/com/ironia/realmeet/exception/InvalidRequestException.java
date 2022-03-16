package com.ironia.realmeet.exception;

import com.ironia.realmeet.validator.ValidationErrors;

public class InvalidRequestException extends RuntimeException{
    private final ValidationErrors validationErrors;

    public InvalidRequestException(ValidationErrors validationErrors) {
        super(validationErrors.toString());
        this.validationErrors = validationErrors;
    }

    public ValidationErrors getValidationErrors() {
        return validationErrors;
    }
}
