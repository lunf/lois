package com.lunf.delilah.data.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class LfMessage implements Serializable {

    private Long id;
    private LfJob job;
    private LfDevice device;
    private String notificationId;
    private int messageType;
    private String messageTitle;
    private String messageBody;
    private String messageMetadata;
}
