package com.lunf.delilah.data.mapper;

import com.lunf.delilah.data.entity.LfDevice;
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
public class LfDeviceMapperTest {
    @Autowired
    private LfDeviceMapper lfDeviceMapper;

    private LfDevice lfDevice;

    private String notificationId = "mkhneO4GJ2mr4XPaGeMnQDDjRixI9k1VxnTGB0PcgDsHzYJzIWyy9WjrnCNgjosl";
    @Before
    public void setup() {
        lfDevice = new LfDevice();
        lfDevice.setCreatedAt(ZonedDateTime.now());
        lfDevice.setDeviceModel("iPhone");
        lfDevice.setDeviceName("JohnDoeiPhone");
        lfDevice.setDeviceOs("iOS");
        lfDevice.setDeviceOsVersion("11.0.3");
        lfDevice.setNotificationId(notificationId);
        lfDevice.setStatus(1);
    }

    @Test
    public void insertDeviceTest() {


        lfDeviceMapper.insert(lfDevice);

        LfDevice foundDevice = lfDeviceMapper.findById(lfDevice.getId());

        assertThat(foundDevice).isNotNull();
        assertThat(foundDevice.getNotificationId()).isEqualTo(notificationId);
    }

    @Test
    public void findDeviceByNotificationIdTest() {

        lfDeviceMapper.insert(lfDevice);

        LfDevice foundDevice = lfDeviceMapper.findByNotificationId(notificationId);

        assertThat(foundDevice).isNotNull();
        assertThat(foundDevice.getNotificationId()).isEqualTo(notificationId);
    }
}
