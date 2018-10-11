package com.lunf.lois.controller.transformer;

import com.lunf.lois.controller.request.DeviceRequest;
import com.lunf.lois.service.model.DeviceDTO;

public class DeviceRequestTransformer {

    public static DeviceDTO transform(DeviceRequest deviceRequest) {
        if (deviceRequest == null) {
            return null;
        }

        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setOsVersion(deviceRequest.getDevice_os_version());
        deviceDTO.setOs(deviceRequest.getDevice_os());
        deviceDTO.setNotificationId(deviceRequest.getNotification_id());
        deviceDTO.setName(deviceRequest.getDevice_name());
        deviceDTO.setModel(deviceRequest.getDevice_model());

        return deviceDTO;
    }
}
