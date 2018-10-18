package com.lunf.lois.controller.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class RawVehicleActivityResponse implements Serializable {

    private long id;
    private String regNo;
    private String date;
    private String departTime;
    private String arrivalTime;
    private String origin;
    private String destination;
    private double gpsDistance;


}
