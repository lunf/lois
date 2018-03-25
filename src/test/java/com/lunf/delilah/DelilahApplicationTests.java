package com.lunf.delilah;

import com.lunf.delilah.business.MessageManager;
import com.lunf.delilah.domain.PushMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DelilahApplicationTests {

	@Autowired
	private MessageManager messageManager;

	private List<PushMessage> loadedMessages;

	@Before
	public void startUp() {
		messageManager.startMessageManager();

		loadedMessages = prepareMessages();

	}

	private List<PushMessage> prepareMessages() {
		List<PushMessage> data = new ArrayList<>();
		for (int index = 0; index < 1000; index ++) {
			PushMessage pushMessage = new PushMessage();
			pushMessage.setDeviceNotificationId(UUID.randomUUID().toString());
			pushMessage.setText(String.valueOf(index));
			data.add(pushMessage);
		}

		return data;
	}

	@Test
	public void processMessage() {
		PushMessage pushMessage = new PushMessage();
		pushMessage.setDeviceNotificationId(UUID.randomUUID().toString());
		pushMessage.setText("Hello world");

		messageManager.process(pushMessage);
	}

	@Test
	public void processLargeMessage() {
		for (PushMessage message: this.loadedMessages) {
			messageManager.process(message);
		}
	}



}
