package com.lunf.lois.service;

import com.lunf.lois.service.exception.DelilahException;
import com.lunf.lois.service.model.DeviceDTO;
import org.springframework.web.multipart.MultipartFile;

public interface VehicleService {
    void uploadVehicleActivityFile(MultipartFile multipartFile) throws DelilahException;
}
