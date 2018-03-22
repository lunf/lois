package com.lunf.delilah.domain;

import lombok.Data;

@Data
public class PushMessage {
    private String deviceNotificationId;
    private String text;
}
