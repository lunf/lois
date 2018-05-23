package com.lunf.delilah.service.impl;

import com.lunf.delilah.data.entity.LfDevice;
import com.lunf.delilah.data.mapper.LfDeviceMapper;
import com.lunf.delilah.data.transformer.LfDeviceTransformer;
import com.lunf.delilah.service.DeviceService;
import com.lunf.delilah.service.constant.ErrorCode;
import com.lunf.delilah.service.exception.DelilahException;
import com.lunf.delilah.service.model.DeviceDTO;
import com.lunf.delilah.service.model.DeviceStatus;
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
