package com.lunf.lois.data.primary.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@ToString
public class LfBookingRequest implements Serializable {
    private Long id;
    private LfUser requester;
    private Integer requestVehicleType;
    private String orderCode;
    private String title;
    private String description;
    private String deliveryAddress;
    private ZonedDateTime createdAt;
    private int status;
    private ZonedDateTime bookedFor;
}
