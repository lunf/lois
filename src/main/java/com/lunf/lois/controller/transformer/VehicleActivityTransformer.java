package com.lunf.lois.controller.transformer;

import com.lunf.lois.controller.response.RawVehicleData;
import com.lunf.lois.service.model.VehicleActivityDTO;
import com.lunf.lois.utilities.DateTimeHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VehicleActivityTransformer {

    public static RawVehicleData transform(VehicleActivityDTO vehicleActivityDTO) {
        RawVehicleData response = new RawVehicleData();

        if (vehicleActivityDTO == null) {
            return  response;
        }

        response.setId(vehicleActivityDTO.getId());
        response.setArrivalTime(DateTimeHelper.convertToTimeString(vehicleActivityDTO.getArrivalTime()));
        response.setDate(DateTimeHelper.convertToDateString(vehicleActivityDTO.getCreatedAt()));
        response.setDepartTime(DateTimeHelper.convertToTimeString(vehicleActivityDTO.getDepartedTime()));
        response.setDestination(vehicleActivityDTO.getDestination());
        response.setGpsDistance(vehicleActivityDTO.getDistanceWithGps());
        response.setOrigin(vehicleActivityDTO.getOrigin());
        response.setRegNo(vehicleActivityDTO.getRegistrationNumber());

        return response;
    }

    public static List<RawVehicleData> transformToList(List<VehicleActivityDTO> activityDTOList) {
        if (activityDTOList == null || activityDTOList.isEmpty()) {
            return new ArrayList<>();
        }

        return activityDTOList.stream().map(dto -> transform(dto)).collect(Collectors.toList());
    }
}
