package com.lunf.lois.data.primary.transformer;

import com.lunf.lois.service.model.DeviceDTO;
import com.lunf.lois.data.primary.entity.LfDevice;

public class LfDeviceTransformer {

    public static LfDevice transform(DeviceDTO deviceDTO) {

        if (deviceDTO == null) {
            return null;
        }

        LfDevice lfDevice = new LfDevice();
        lfDevice.setStatus(deviceDTO.getStatus().ordinal());
        lfDevice.setNotificationId(deviceDTO.getNotificationId());
        lfDevice.setDeviceOsVersion(deviceDTO.getOsVersion());
        lfDevice.setDeviceOs(deviceDTO.getOs());
        lfDevice.setDeviceName(deviceDTO.getName());
        lfDevice.setDeviceModel(deviceDTO.getModel());
        lfDevice.setCreatedAt(deviceDTO.getCreatedAt());
        return lfDevice;
    }

}
