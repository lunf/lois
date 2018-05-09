package com.lunf.delilah.data.mapper;

import com.lunf.delilah.data.entity.LfDevice;
import com.lunf.delilah.data.entity.LfJob;
import com.lunf.delilah.data.entity.LfMessage;
import com.lunf.delilah.data.entity.LfUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@MybatisTest
public class LfMessageMapperTest {

    @Autowired
    private LfMessageMapper lfMessageMapper;

    @Autowired
    private LfUserMapper lfUserMapper;

    @Autowired
    private LfDeviceMapper lfDeviceMapper;

    @Autowired
    private LfJobMapper lfJobMapper;

    private LfDevice lfDevice;

    private LfJob lfJob;

    private LfUser lfUser;

    private String notificationId = "mkhneO4GJ2mr4XPaGeMnQDDjRixI9k1VxnTGB0PcgDsHzYJzIWyy9WjrnCNgjosl";

    private String messageTitle = "This is message title";

    private String messageBody = "This is message body";

    private String messageMetadata = "{\"key_1\":\"value_1\", \"key_2\":\"value_2\"}";

    private int messageType = 1;

    private String username = "jane.doe@domain.com";

    @Before
    public void setup() {

        lfUser = new LfUser();
        lfUser.setUsername(username);
        lfUser.setFirstName("Jane");
        lfUser.setLastName("Doe");

        lfUserMapper.insert(lfUser);


        lfDevice = new LfDevice();
        lfDevice.setCreatedAt(ZonedDateTime.now());
        lfDevice.setDeviceModel("iPhone");
        lfDevice.setDeviceName("JohnDoeiPhone");
        lfDevice.setDeviceOs("iOS");
        lfDevice.setDeviceOsVersion("11.0.3");
        lfDevice.setNotificationId(notificationId);
        lfDevice.setStatus(1);

        lfDeviceMapper.insert(lfDevice);


        lfJob = new LfJob();

        lfJob.setCreatedAt(ZonedDateTime.now());
        lfJob.setDevices("{\"g2ZnDeZfGZ6CFmmGiYTzxTjfQHyTD4VcmzPZuYoAmpbgR4MTxL0KILfkk0yKtay8\", \"ms3tUJHDc8TX2kkTOZB180tZlnBjJf66D8sOaqeopdbJK5nKaHy9hXIicXb1EqFS\"}");
        lfJob.setMessageBody(messageBody);
        lfJob.setMessageMetadata(messageMetadata);
        lfJob.setMessageType(messageType);
        lfJob.setMessageTitle(messageTitle);
        lfJob.setSendToType(4);
        lfJob.setSender(lfUser);

        lfJobMapper.insert(lfJob);
    }

    @Test
    public void insertLfMessageTest() {

        LfMessage lfMessage = new LfMessage();
        lfMessage.setDevice(lfDevice);
        lfMessage.setJob(lfJob);
        lfMessage.setMessageBody(messageBody);
        lfMessage.setMessageTitle(messageTitle);
        lfMessage.setMessageMetadata(messageMetadata);
        lfMessage.setNotificationId(notificationId);
        lfMessage.setMessageType(messageType);

        lfMessageMapper.insert(lfMessage);

        LfMessage foundMessage = lfMessageMapper.findById(lfMessage.getId());

        assertThat(foundMessage).isNotNull();
        assertThat(foundMessage.getNotificationId()).isEqualTo(notificationId);
        assertThat(foundMessage.getDevice().getNotificationId()).isEqualTo(notificationId);
        assertThat(foundMessage.getJob().getMessageTitle()).isEqualTo(messageTitle);
        assertThat(foundMessage.getJob().getSender().getUsername()).isEqualTo(username);
    }
}
