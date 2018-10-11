package com.lunf.lois.service.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DeviceStatus {
    NOK(0), OK(1);

    private final int value;
}
