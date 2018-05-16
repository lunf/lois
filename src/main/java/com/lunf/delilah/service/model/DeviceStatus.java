package com.lunf.delilah.service.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DeviceStatus {
    NOK(0), OK(1);

    private final int value;
}
