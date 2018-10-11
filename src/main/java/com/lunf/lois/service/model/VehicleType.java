package com.lunf.lois.service.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum VehicleType {
    VAN_16(1), TRUCK_15(2), PICK_UP(3);
    private final int value;
}
