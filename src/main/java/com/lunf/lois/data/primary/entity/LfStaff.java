package com.lunf.lois.data.primary.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@ToString
public class LfStaff implements Serializable {
    private Long id;
    private String name;
    private String mobileNumber;
    private String driverLicenseNumber;
    private ZonedDateTime licenseExpiredDate;
    private int status;
}
