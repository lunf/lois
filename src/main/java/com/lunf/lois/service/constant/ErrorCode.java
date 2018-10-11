package com.lunf.lois.service.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode {

    // Validation error, start at 1xxx
    FAIL_VALIDATION(1000,"Fail validation"),

    // Data integrity error, start at 2xxx
    DUPLICATE(2000, "Duplicate"),

    // Data conversion error, start at 3xxx
    PARSING_DATA_ERROR(3000, "Unable to parse data");

    private final int code;
    private final String message;
}