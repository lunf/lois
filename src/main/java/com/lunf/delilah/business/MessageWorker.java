package com.lunf.delilah.business;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.lmax.disruptor.WorkHandler;
import com.lunf.delilah.domain.MessageEvent;
import com.lunf.delilah.domain.PushMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class MessageWorker implements WorkHandler<MessageEvent> {

    private static final Logger logger = LoggerFactory.getLogger(MessageWorker.class);

    @Value("${firebase.dryRun}")
    private boolean dryRun;

    @Override
    public void onEvent(MessageEvent event) {

        try {
            PushMessage pushMessage = event.getPushMessage();

            Notification notification = new Notification("", pushMessage.getText());

            Message message = Message.builder()
                    .setNotification(notification)
                    .setToken(pushMessage.getDeviceNotificationId())
                    .build();

            String response = FirebaseMessaging.getInstance().sendAsync(message, dryRun).get();

            logger.info("Message {} deliver to FCM server with response {}", pushMessage.getText(), response);

        } catch (Exception e) {
            logger.error("Exception Processing Message ", e);
        }

    }
}
