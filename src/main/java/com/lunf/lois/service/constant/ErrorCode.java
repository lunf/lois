package com.lunf.lois.service.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ErrorCode {

    // Validation error, start at 1xxx
    FAIL_VALIDATION(1000,"Fail validation"),
    DATA_EMPTY(1001, "Data empty"),

    // Data integrity error, start at 2xxx
    DUPLICATE(2000, "Duplicate"),
    DUPLICATE_USER_NAME(2001, "Username taken"),

    // Data conversion error, start at 3xxx
    PARSING_DATA_ERROR(3000, "Unable to parse data");

    private final int code;
    private final String message;
}
