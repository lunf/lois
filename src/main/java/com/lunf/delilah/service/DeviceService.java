package com.lunf.delilah.service;

import com.lunf.delilah.service.exception.DelilahException;
import com.lunf.delilah.service.model.DeviceDTO;

public interface DeviceService {
    void insertDevice(DeviceDTO deviceDTO) throws DelilahException;
}
