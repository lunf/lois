package com.lunf.lois.service;

import com.lunf.lois.service.constant.ErrorCode;
import com.lunf.lois.service.exception.DelilahException;
import com.lunf.lois.service.impl.DeviceServiceImpl;
import com.lunf.lois.service.model.DeviceDTO;
import com.lunf.lois.service.model.DeviceStatus;
import org.assertj.core.api.Assertions;
import com.lunf.lois.data.primary.mapper.LfDeviceMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@MybatisTest
public class DeviceServiceTests {

    private DeviceService deviceService;

    @Autowired
    private LfDeviceMapper lfDeviceMapper;

    private DeviceDTO deviceDTO;

    @Before
    public void setup() {
        deviceService = new DeviceServiceImpl(lfDeviceMapper);

        deviceDTO = new DeviceDTO();
        deviceDTO.setCreatedAt(ZonedDateTime.now());
        deviceDTO.setModel("iPhone");
        deviceDTO.setName("JoeDoeiPhone");
        deviceDTO.setNotificationId("VeryLongIdWhichIDontWantToGenerated");
        deviceDTO.setOs("iOS");
        deviceDTO.setStatus(DeviceStatus.OK);
        deviceDTO.setOsVersion("11.1");
    }

    @Test
    public void insertDeviceServiceTest() throws Exception {

        deviceService.insertDevice(deviceDTO);

        assertThat(deviceDTO.getId()).isNotNull();
        assertThat(deviceDTO.getId()).isEqualTo(0L);
    }

    @Test
    public void insertDuplicateDeviceTest() {

        try {
            deviceService.insertDevice(deviceDTO);
            deviceService.insertDevice(deviceDTO);
        } catch (DelilahException ex) {
            Assertions.assertThat(ex.getErrorCode()).isEqualByComparingTo(ErrorCode.DUPLICATE);
        }
    }
}
