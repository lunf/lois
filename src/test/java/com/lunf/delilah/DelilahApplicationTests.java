package com.lunf.delilah;

import com.lunf.delilah.business.MessageManager;
import com.lunf.delilah.config.AppConfig;
import com.lunf.delilah.config.DisruptorConfig;
import com.lunf.delilah.config.FirebaseConfig;
import com.lunf.delilah.domain.PushMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {FirebaseConfig.class, AppConfig.class, DisruptorConfig.class})
public class DelilahApplicationTests {

	@Autowired
	private MessageManager messageManager;

	private List<PushMessage> loadedMessages;

	private String notificationId = "cDIgA634LOI:APA91bEkw0VbPdkcAIn-uvkWDqbmpi1kPLWYECiEJHmvRxYw59k2D1Obh6jwmFWnSWxHlYRVLrOULSwRdi55wHAhPlJwKTCPg5Ht_QKAdZJ-4-aRfJ_Wm4ov5bCYA7J9FP4pmwKnKYHz";
	@Before
	public void startUp() {
		messageManager.startMessageManager();

		loadedMessages = prepareMessages();

	}

	private List<PushMessage> prepareMessages() {
		List<PushMessage> data = new ArrayList<>();
		for (int index = 0; index < 1000; index ++) {
			PushMessage pushMessage = new PushMessage();
			pushMessage.setDeviceNotificationId(notificationId);
			pushMessage.setText(String.valueOf(index));
			data.add(pushMessage);
		}

		return data;
	}

	@Test
	public void processMessage() throws Exception {
		PushMessage pushMessage = new PushMessage();
		pushMessage.setDeviceNotificationId(notificationId);
		pushMessage.setText("Hello world");

		messageManager.process(pushMessage);

		Thread.sleep(2000);
	}

	@Test
	public void processLargeMessage() {
		for (PushMessage message: this.loadedMessages) {
			messageManager.process(message);
		}
	}
}
