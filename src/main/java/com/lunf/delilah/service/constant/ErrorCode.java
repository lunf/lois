package com.lunf.delilah.service.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode {
    DUPLICATE(1000, "Duplicate");

    private final int code;
    private final String message;
}
