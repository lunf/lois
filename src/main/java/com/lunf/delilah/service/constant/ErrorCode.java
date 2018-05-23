package com.lunf.delilah.service.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode {
    DUPLICATE(1000, "Duplicate"),
    FAIL_VALIDATION(2000,"Fail validation");

    private final int code;
    private final String message;
}
