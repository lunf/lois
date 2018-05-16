package com.lunf.delilah.data.transformer;

import com.lunf.delilah.data.entity.LfDevice;
import com.lunf.delilah.service.model.DeviceDTO;

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
