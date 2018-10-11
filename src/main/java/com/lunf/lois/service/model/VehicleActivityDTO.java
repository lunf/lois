package com.lunf.lois.service.model;

import lombok.Data;
import lombok.ToString;

import java.time.LocalTime;
import java.time.ZonedDateTime;

@Data
@ToString
public class VehicleActivityDTO {
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
