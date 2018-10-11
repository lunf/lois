package com.lunf.lois.service;

import com.lunf.lois.service.exception.DelilahException;
import com.lunf.lois.service.model.DeviceDTO;

public interface DeviceService {
    void insertDevice(DeviceDTO deviceDTO) throws DelilahException;
}
