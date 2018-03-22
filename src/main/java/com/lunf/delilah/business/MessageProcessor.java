package com.lunf.delilah.business;

import com.lmax.disruptor.WorkHandler;
import com.lunf.delilah.domain.MessageEvent;
import com.lunf.delilah.domain.PushMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageProcessor implements WorkHandler<MessageEvent> {

    private static final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    @Override
    public void onEvent(MessageEvent event) throws Exception {

        try {
            PushMessage message = event.getPushMessage();
            logger.info(message.getDeviceNotificationId());
            logger.info(message.getText());
        } catch (Exception e) {
            logger.error("Exception Processing Message ", e);
        }

    }
}
