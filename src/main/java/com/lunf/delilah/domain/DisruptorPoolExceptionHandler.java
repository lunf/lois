package com.lunf.delilah.domain;

import com.lmax.disruptor.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DisruptorPoolExceptionHandler implements ExceptionHandler<MessageEvent> {

    private static final Logger logger = LoggerFactory.getLogger(DisruptorPoolExceptionHandler.class);

    @Override
    public void handleEventException(Throwable ex, long sequence, MessageEvent event) {
        logger.error("Exception processing message {}", event.getPushMessage().getDeviceNotificationId(), ex);
    }

    @Override
    public void handleOnStartException(Throwable ex) {
        logger.error("Exception On Shutdown", ex);
    }

    @Override
    public void handleOnShutdownException(Throwable ex) {
        logger.error("Exception On StartUp", ex);
    }
}
