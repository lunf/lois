package com.lunf.lois.controller.transformer;

import com.lunf.lois.controller.response.RawVehicleActivityResponse;
import com.lunf.lois.service.model.VehicleActivityDTO;
import com.lunf.lois.utilities.DateTimeHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VehicleActivityTransformer {

    public static RawVehicleActivityResponse transform(VehicleActivityDTO vehicleActivityDTO) {
        RawVehicleActivityResponse response = new RawVehicleActivityResponse();

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

    public static List<RawVehicleActivityResponse> transformToList(List<VehicleActivityDTO> activityDTOList) {
        if (activityDTOList == null || activityDTOList.isEmpty()) {
            return new ArrayList<>();
        }

        return activityDTOList.stream().map(dto -> transform(dto)).collect(Collectors.toList());
    }
}
