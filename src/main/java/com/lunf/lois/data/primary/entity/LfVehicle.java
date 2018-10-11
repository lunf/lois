package com.lunf.lois.data.primary.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@ToString
public class LfVehicle implements Serializable {

    private Long id;
    private ZonedDateTime createdAt;
    private String registrationNumber;
    private ZonedDateTime purchasedDate;
    private String maker;
    private String model;
    private Integer manufactureYear;
    private int type;
    private Integer odometer;
    private int status;
}
