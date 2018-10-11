package com.lunf.lois.data.primary.mapper;

import com.lunf.lois.data.primary.PrimaryMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.lunf.lois.data.primary.entity.LfVehicleActivity;

@PrimaryMapper
public interface LfVehicleActivityMapper {

    @Insert("insert into lf_vehicle_activity (registration_number, created_at, departed_time, " +
            "arrival_time, origin, destination, total_running_in_minute, total_pause_in_minute, distance_with_gps, " +
            "number_of_stop_start, max_speed, average_speed)" +
            " values (#{registrationNumber}, #{createdAt}, #{departedTime}, #{arrivalTime}, #{origin}, #{destination}, " +
            "#{totalRunningInMinute}, #{totalPauseInMinute}, #{distanceWithGps}, #{numberOfStopStart}, #{maxSpeed}, #{averageSpeed})")
    @Options(useGeneratedKeys=true, keyColumn = "id")
    void insert(LfVehicleActivity lfVehicleActivity);

    @Select("select * from lf_vehicle_activity where id = #{id}")
    LfVehicleActivity findById(@Param("id") Long id);

}
