package com.lunf.lois.service;

import com.lunf.lois.service.exception.DelilahException;
import com.lunf.lois.service.model.VehicleActivityDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface VehicleService {
    void uploadVehicleActivityFile(MultipartFile multipartFile) throws DelilahException;
    List<VehicleActivityDTO> findRawVehicleReportPaginated(int pageNo, int limit, Map<String, String> sort) throws DelilahException;
    void deleteRawVehicleReport(List<Long> deleteIdList) throws DelilahException;
}
