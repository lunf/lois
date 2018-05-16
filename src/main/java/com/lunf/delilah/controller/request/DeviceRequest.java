package com.lunf.delilah.controller.request;

import lombok.Data;

@Data
public class DeviceRequest {
    private Long id;
    private String created_at;
    private String notification_id;
    private String device_os;
    private String device_name;
    private String device_os_version;
    private String device_model;
}
