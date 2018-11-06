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

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Comparator;
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

    @Override
    public VehicleActivityDTO mergeRawVehicleReport(List<Long> mergeIdList) throws DelilahException {

        List<LfVehicleActivity> foundVehicleActivity = lfVehicleActivityMapper.findByIds(mergeIdList);

        if (foundVehicleActivity == null || foundVehicleActivity.isEmpty()) {
            throw new DelilahException(ErrorCode.DATA_EMPTY);
        }

        if (foundVehicleActivity.size() != mergeIdList.size()) {
            throw new DelilahException(ErrorCode.DATA_INCONSISTENCY);
        }

        // Create new records and remove the old one
        List<VehicleActivityDTO> activityDTOList = LfVehicleTransformer.transformToDtoList(foundVehicleActivity);

        // Sort the list by depart time
        Collections.sort(activityDTOList, Comparator.comparing(VehicleActivityDTO::getDepartedTime));

        // First item in the merged list
        VehicleActivityDTO firstItem = activityDTOList.get(0);
        VehicleActivityDTO lastItem = activityDTOList.get(activityDTOList.size() -1);

        // all registration of the merge is the same
        String regNo = firstItem.getRegistrationNumber();
        ZonedDateTime createdDate = firstItem.getCreatedAt();

        for (VehicleActivityDTO activityDTO : activityDTOList) {
            String checkReg = activityDTO.getRegistrationNumber();

            if (!checkReg.equalsIgnoreCase(regNo)) {
                ErrorCode error = ErrorCode.DATA_INCONSISTENCY;
                error.setDetailMessage("Could not merge if Registration numbers are not the same");
                throw new DelilahException(error);
            }

            ZonedDateTime checkDate = activityDTO.getCreatedAt();

            if (!checkDate.isEqual(createdDate)) {
                ErrorCode error = ErrorCode.DATA_INCONSISTENCY;
                error.setDetailMessage("Could not merge if Created dates are not in the same day");
                throw new DelilahException(error);

            }
        }



        VehicleActivityDTO mergedVehicleActivity = new VehicleActivityDTO();

        mergedVehicleActivity.setRegistrationNumber(regNo);
        mergedVehicleActivity.setCreatedAt(createdDate);

        // Get depart from the first item
        mergedVehicleActivity.setDepartedTime(firstItem.getDepartedTime());
        mergedVehicleActivity.setOrigin(firstItem.getOrigin());

        // Get arrival time from the last item
        mergedVehicleActivity.setArrivalTime(lastItem.getArrivalTime());
        mergedVehicleActivity.setDestination(lastItem.getDestination());

        // Calculate average for other properties
        Double averageSpeed = Double.NaN;
        Double maxSpeed = Double.NaN;
        Integer numberOfStopStart = 0;
        Integer totalPauseInMin = 0;
        Integer totalRunningInMin = 0;
        Double distanceWithGps = 0.0;
        for (VehicleActivityDTO activityDTO : activityDTOList) {
            averageSpeed = + activityDTO.getAverageSpeed();
            maxSpeed = + activityDTO.getMaxSpeed();
            numberOfStopStart = + activityDTO.getNumberOfStopStart();
            totalPauseInMin = + activityDTO.getTotalPauseInMinute();
            totalRunningInMin = + activityDTO.getTotalRunningInMinute();
            distanceWithGps = + activityDTO.getDistanceWithGps();
        }
        averageSpeed = averageSpeed / activityDTOList.size();
        maxSpeed = maxSpeed / activityDTOList.size();


        mergedVehicleActivity.setAverageSpeed(averageSpeed);
        mergedVehicleActivity.setMaxSpeed(maxSpeed);
        mergedVehicleActivity.setNumberOfStopStart(numberOfStopStart);
        mergedVehicleActivity.setTotalPauseInMinute(totalPauseInMin);
        mergedVehicleActivity.setTotalRunningInMinute(totalRunningInMin);
        mergedVehicleActivity.setDistanceWithGps(distanceWithGps);

        LfVehicleActivity mergedActivity = LfVehicleTransformer.transform(mergedVehicleActivity);
        lfVehicleActivityMapper.insert(mergedActivity);

        mergedVehicleActivity.setId(mergedActivity.getId());
        
        return mergedVehicleActivity;
    }
}
