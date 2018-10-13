package com.lunf.lois.data.primary.transformer;

import com.lunf.lois.data.primary.entity.LfVehicleActivity;
import com.lunf.lois.service.model.VehicleActivityDTO;

import java.util.List;
import java.util.stream.Collectors;

public class LfVehicleTransformer {


    public static LfVehicleActivity transform(VehicleActivityDTO vehicleActivityDTO) {

        LfVehicleActivity lfVehicleActivity = new LfVehicleActivity();

        if (vehicleActivityDTO == null) {
            return lfVehicleActivity;
        }


        lfVehicleActivity.setArrivalTime(vehicleActivityDTO.getArrivalTime());
        lfVehicleActivity.setAverageSpeed(vehicleActivityDTO.getAverageSpeed());
        lfVehicleActivity.setCreatedAt(vehicleActivityDTO.getCreatedAt());
        lfVehicleActivity.setDepartedTime(vehicleActivityDTO.getDepartedTime());
        lfVehicleActivity.setDestination(vehicleActivityDTO.getDestination());
        lfVehicleActivity.setDistanceWithGps(vehicleActivityDTO.getDistanceWithGps());
        lfVehicleActivity.setMaxSpeed(vehicleActivityDTO.getMaxSpeed());
        lfVehicleActivity.setNumberOfStopStart(vehicleActivityDTO.getNumberOfStopStart());
        lfVehicleActivity.setOrigin(vehicleActivityDTO.getOrigin());
        lfVehicleActivity.setRegistrationNumber(vehicleActivityDTO.getRegistrationNumber());
        lfVehicleActivity.setTotalPauseInMinute(vehicleActivityDTO.getTotalPauseInMinute());
        lfVehicleActivity.setId(vehicleActivityDTO.getId());


        return lfVehicleActivity;
    }

    public static List<LfVehicleActivity> transformList(List<VehicleActivityDTO> activityDTOList) {

        List<LfVehicleActivity> changed = activityDTOList.stream()
                .map( it -> transform(it) ).collect(Collectors.toList());

        return changed;
    }
}
