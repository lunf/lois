package com.lunf.lois.data.primary.mapper;

import com.lunf.lois.data.primary.PrimaryMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.lunf.lois.data.primary.entity.LfVehicleTrip;

@PrimaryMapper
public interface LfVehicleTripMapper {
    @Insert("insert into lf_vehicle_trip (registration_number, created_at, departed_time, " +
            "arrival_time, origin, destination, total_running_in_minute, total_pause_in_minute, distance_with_gps, " +
            "distance_with_map, number_of_stop_start, max_speed, average_speed)" +
            " values (#{registrationNumber}, #{createdAt}, #{departedTime}, #{arrivalTime}, #{origin}, #{destination}, " +
            "#{totalRunningInMinute}, #{totalPauseInMinute}, #{distanceWithGps}, #{distanceWithMap}, #{numberOfStopStart}, #{maxSpeed}, #{averageSpeed})")
    @Options(useGeneratedKeys=true, keyColumn = "id")
    void insert(LfVehicleTrip lfVehicleTrip);

    @Select("select * from lf_vehicle_trip where id = #{id}")
    LfVehicleTrip findById(@Param("id") Long id);
}
