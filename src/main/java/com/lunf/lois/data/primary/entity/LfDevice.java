package com.lunf.lois.data.primary.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@ToString
public class LfDevice implements Serializable {

    private Long id;
    private ZonedDateTime createdAt;
    private String notificationId;
    private int status;
    private String deviceOs;
    private String deviceName;
    private String deviceOsVersion;
    private String deviceModel;
}
