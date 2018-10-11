package com.lunf.lois.data.primary.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@ToString
public class LfOrder implements Serializable {
    private Long id;
    private String name;
    private String code;
    private ZonedDateTime estimatedCompletionDate;
    private String deliveryAddress;
    private Integer bookingQuota;
    private int type;
}
