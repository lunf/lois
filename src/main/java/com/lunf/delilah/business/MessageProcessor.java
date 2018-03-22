package com.lunf.delilah.business;

import com.lmax.disruptor.WorkHandler;
import com.lunf.delilah.domain.MessageEvent;

public class MessageProcessor implements WorkHandler<MessageEvent> {
    @Override
    public void onEvent(MessageEvent event) throws Exception {

    }
}
