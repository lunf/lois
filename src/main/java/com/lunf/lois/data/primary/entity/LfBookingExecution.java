package com.lunf.lois.data.primary.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class LfBookingExecution implements Serializable {

    private Long id;
    private LfStaff driver;
    private LfBookingRequest bookingRequest;
    private LfVehicleTrip vehicleTrip;
}
