package com.lunf.lois.data.primary.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.ZonedDateTime;

@Data
@ToString
public class LfVehicleActivity implements Serializable {
    private Long id;
    private String registrationNumber;
    private ZonedDateTime createdAt;
    private LocalTime departedTime;
    private LocalTime arrivalTime;
    private String origin;
    private String destination;
    private Integer totalRunningInMinute;
    private Integer totalPauseInMinute;
    private Double distanceWithGps;
    private Integer numberOfStopStart;
    private Double maxSpeed;
    private Double averageSpeed;
}
