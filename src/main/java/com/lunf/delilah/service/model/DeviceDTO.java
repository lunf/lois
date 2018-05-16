package com.lunf.delilah.service.model;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class DeviceDTO {
    private Long id;
    private String notificationId;
    private DeviceStatus status;
    private String os;
    private String name;
    private String osVersion;
    private String model;
    private ZonedDateTime createdAt;
}
