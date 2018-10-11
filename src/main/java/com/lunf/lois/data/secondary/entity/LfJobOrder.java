package com.lunf.lois.data.secondary.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class LfJobOrder implements Serializable {
    private String code;
    private Date createdDate;
    private Date updatedDate;
    private String shipAddress;
    private String deployAddress;
    private int jobType;
    private int status;
}
