package com.lunf.lois.service;

import com.lunf.lois.service.exception.DelilahException;
import com.lunf.lois.service.model.VehicleActivityDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExcelDataService {
    List<VehicleActivityDTO> processVehicleData(MultipartFile uploadedFile) throws DelilahException;
}
