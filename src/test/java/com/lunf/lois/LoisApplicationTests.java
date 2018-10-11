package com.lunf.lois;

import com.lunf.lois.domain.PushMessage;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoisApplicationTests {


	private List<PushMessage> loadedMessages;

	private String notificationId = "cDIgA634LOI:APA91bEkw0VbPdkcAIn-uvkWDqbmpi1kPLWYECiEJHmvRxYw59k2D1Obh6jwmFWnSWxHlYRVLrOULSwRdi55wHAhPlJwKTCPg5Ht_QKAdZJ-4-aRfJ_Wm4ov5bCYA7J9FP4pmwKnKYHz";
	@Before
	public void startUp() {
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
}
