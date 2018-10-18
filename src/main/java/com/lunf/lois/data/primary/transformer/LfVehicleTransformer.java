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

    public static VehicleActivityDTO transform(LfVehicleActivity lfVehicleActivity) {

        VehicleActivityDTO activityDTO = new VehicleActivityDTO();

        if (lfVehicleActivity == null) {
            return activityDTO;
        }

        activityDTO.setArrivalTime(lfVehicleActivity.getArrivalTime());
        activityDTO.setAverageSpeed(lfVehicleActivity.getAverageSpeed());
        activityDTO.setCreatedAt(lfVehicleActivity.getCreatedAt());
        activityDTO.setDepartedTime(lfVehicleActivity.getDepartedTime());
        activityDTO.setDestination(lfVehicleActivity.getDestination());
        activityDTO.setDistanceWithGps(lfVehicleActivity.getDistanceWithGps());
        activityDTO.setMaxSpeed(lfVehicleActivity.getMaxSpeed());
        activityDTO.setNumberOfStopStart(lfVehicleActivity.getNumberOfStopStart());
        activityDTO.setOrigin(lfVehicleActivity.getOrigin());
        activityDTO.setRegistrationNumber(lfVehicleActivity.getRegistrationNumber());
        activityDTO.setTotalPauseInMinute(lfVehicleActivity.getTotalPauseInMinute());
        activityDTO.setId(lfVehicleActivity.getId());


        return activityDTO;
    }

    public static List<LfVehicleActivity> transformFromDtoList(List<VehicleActivityDTO> activityDTOList) {

        List<LfVehicleActivity> changed = activityDTOList.stream()
                .map( it -> transform(it) ).collect(Collectors.toList());

        return changed;
    }

    public static List<VehicleActivityDTO> transformToDtoList(List<LfVehicleActivity> lfVehicleActivities) {

        List<VehicleActivityDTO> changed = lfVehicleActivities.stream()
                .map(it -> transform(it)).collect(Collectors.toList());

        return changed;
    }
}
