package com.lunf.delilah;

import com.lunf.delilah.business.MessageManager;
import com.lunf.delilah.domain.PushMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DelilahApplicationTests {

	@Autowired
	private MessageManager messageManager;

	@Before
	public void startUp() {
		messageManager.startMessageManager();
	}

	@Test
	public void processMessage() {
		PushMessage pushMessage = new PushMessage();
		pushMessage.setDeviceNotificationId(UUID.randomUUID().toString());
		pushMessage.setText("Hello world");

		messageManager.process(pushMessage);
	}



}
