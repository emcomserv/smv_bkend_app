/*************************************************
 * Copyright (C) Kolacut Company, Inc. All rights reserved.
 * This file is for internal use only at Kolacut Company, Inc.
 *************************************************/
package com.smartbus.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private final boolean status;
    private final LocalDateTime timeStamp;
    private final String path;
    private final String message;
    private List<ValidationError> errors;
    private List<FieldValidationError> fieldValidationErrors;
    private String stackTrace;

    @RequiredArgsConstructor
    @Data
    private static class ValidationError {
        private final String message;
    }

    @RequiredArgsConstructor
    @Data
    public static class FieldValidationError {
        private final String fieldName;
        private final String message;
    }

    public void addValidationError(String message) {
        if (Objects.isNull(errors)) {
            errors = new ArrayList<>();
        }
        errors.add(new ValidationError(message));
    }

    public void addFieldValidationError(String fieldName, String message) {
        if (Objects.isNull(fieldValidationErrors)) {
            fieldValidationErrors = new ArrayList<>();
        }
        fieldValidationErrors.add(new FieldValidationError(fieldName, message));
    }
}
