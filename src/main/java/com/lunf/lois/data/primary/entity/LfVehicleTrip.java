package com.lunf.lois.data.primary.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.ZonedDateTime;

@Data
@ToString
public class LfVehicleTrip implements Serializable {
    private Long id;
    private String registrationNumber;
    private ZonedDateTime createdAt;
    private LocalTime departedTime;
    private LocalTime arrivalTime;
    private String origin;
    private Location originLocation;
    private String destination;
    private Location destinationLocation;
    private Integer totalRunningInMinute;
    private Integer totalPauseInMinute;
    private Double distanceWithGps;
    private Double distanceWithMap;
    private Integer numberOfStopStart;
    private Double maxSpeed;
    private Double averageSpeed;

    @Data
    public class Location {
        private double latitude;
        private double longitude;
    }
}
