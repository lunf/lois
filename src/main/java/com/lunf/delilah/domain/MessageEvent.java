package com.lunf.delilah.domain;

import com.lmax.disruptor.EventFactory;
import lombok.Data;

@Data
public class MessageEvent {

    private PushMessage pushMessage;

    public static final EventFactory<MessageEvent> EVENT_FACTORY = () -> new MessageEvent();
}
