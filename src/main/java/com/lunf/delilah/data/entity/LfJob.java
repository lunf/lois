package com.lunf.delilah.data.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@ToString
public class LfJob implements Serializable {
    private Long id;
    private ZonedDateTime createdAt;
    private ZonedDateTime processingAt;
    private ZonedDateTime completedAt;
    private LfUser sender;
    private int messageType;
    private String messageTitle;
    private String messageBody;
    private String messageMetadata;
    private int sendToType;
    private String devices;
}
