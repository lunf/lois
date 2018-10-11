package com.lunf.lois.service.impl;

import com.lunf.lois.service.DeviceService;
import com.lunf.lois.service.constant.ErrorCode;
import com.lunf.lois.service.exception.DelilahException;
import com.lunf.lois.service.model.DeviceDTO;
import com.lunf.lois.service.model.DeviceStatus;
import com.lunf.lois.data.primary.entity.LfDevice;
import com.lunf.lois.data.primary.mapper.LfDeviceMapper;
import com.lunf.lois.data.primary.transformer.LfDeviceTransformer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@AllArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final LfDeviceMapper lfDeviceMapper;

    @Override
    public void insertDevice(DeviceDTO deviceDTO) throws DelilahException {

        // check if the device is existed
        String notificationId = deviceDTO.getNotificationId();

        LfDevice lfDevice = lfDeviceMapper.findByNotificationId(notificationId);

        if (lfDevice == null) {

            deviceDTO.setCreatedAt(ZonedDateTime.now());

            // set device status to OK
            deviceDTO.setStatus(DeviceStatus.OK);

            // insert the device into db
            lfDevice = LfDeviceTransformer.transform(deviceDTO);

            lfDeviceMapper.insert(lfDevice);

            // Update database Id for new device
            deviceDTO.setId(lfDevice.getId());
        } else {
            throw new DelilahException(ErrorCode.DUPLICATE);
        }
    }
}
