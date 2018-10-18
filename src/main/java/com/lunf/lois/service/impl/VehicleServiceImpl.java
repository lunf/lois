package com.lunf.lois.service.impl;

import com.lunf.lois.data.primary.entity.LfVehicleActivity;
import com.lunf.lois.data.primary.mapper.LfVehicleActivityMapper;
import com.lunf.lois.data.primary.transformer.LfVehicleTransformer;
import com.lunf.lois.service.ExcelDataService;
import com.lunf.lois.service.VehicleService;
import com.lunf.lois.service.constant.ErrorCode;
import com.lunf.lois.service.exception.DelilahException;
import com.lunf.lois.service.model.VehicleActivityDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);

    @Autowired
    private LfVehicleActivityMapper lfVehicleActivityMapper;

    private final ExcelDataService excelDataService;

    @Override
    public void uploadVehicleActivityFile(MultipartFile multipartFile) throws DelilahException {
        List<VehicleActivityDTO> activityList = excelDataService.processVehicleData(multipartFile);

        if (activityList == null || activityList.size() < 1) {
            throw new DelilahException(ErrorCode.DATA_EMPTY);
        }

        List transformedList = LfVehicleTransformer.transformFromDtoList(activityList);

        try {
            lfVehicleActivityMapper.insertBatch(transformedList);
        } catch (Exception ex) {
            logger.debug("Fail to delete raw vehicle report", ex);

            ErrorCode errorCode = ErrorCode.ERROR_IN_DATABASE_LAYER;
            errorCode.setDetailMessage(ex.getMessage());

            throw new DelilahException(errorCode);
        }

    }

    @Override
    public List<VehicleActivityDTO> findRawVehicleReportPaginated(int pageNo, int limit, Map<String, String> sort) throws DelilahException {

        int offset = pageNo * limit;
        try {
            List<LfVehicleActivity> vehicleActivities = lfVehicleActivityMapper.findAll(offset, limit, sort);

            return LfVehicleTransformer.transformToDtoList(vehicleActivities);
        } catch (Exception ex) {
            logger.debug("Fail to find raw vehicle report paginated", ex);

            ErrorCode errorCode = ErrorCode.ERROR_IN_DATABASE_LAYER;
            errorCode.setDetailMessage(ex.getMessage());

            throw new DelilahException(errorCode);
        }
    }

    @Override
    public void deleteRawVehicleReport(List<Long> deleteIdList) throws DelilahException {
        try {
            lfVehicleActivityMapper.deleteByIds(deleteIdList);
        } catch (Exception ex) {
            logger.debug("Fail to delete raw vehicle report", ex);

            ErrorCode errorCode = ErrorCode.ERROR_IN_DATABASE_LAYER;
            errorCode.setDetailMessage(ex.getMessage());

            throw new DelilahException(errorCode);
        }
    }
}
