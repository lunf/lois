package com.lunf.lois.data.primary.mapper;

import com.lunf.lois.data.primary.PrimaryMapper;
import com.lunf.lois.data.primary.entity.LfVehicleActivity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

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

    @Select("<script>" +
            "select * from lf_vehicle_activity " +
            "<if test='idList != null and !idList.isEmpty()'>" +
                "WHERE id IN " +
                "<foreach item='id' collection='idList' open='(' separator=',' close=')'>" +
                    "#{id}" +
                "</foreach>" +
            "</if>" +
            "LIMIT ${idList.size()}" +
    "</script>")
    List<LfVehicleActivity> findByIds(@Param("idList") List<Long> ids);

    @Select("select * from lf_vehicle_activity where registration_number = #{rego}")
    List<LfVehicleActivity> findByRego(@Param("rego") String rego);

    @Select("<script>" +
            "select * from lf_vehicle_activity " +
            "<if test='sort != null and !sort.isEmpty()'>" +
                "ORDER BY " +
                "<foreach index='index' item='entry' collection='sort.entrySet()' separator=','>" +
                    "${index} ${entry}" +
                "</foreach> " +
            "</if>" +
            "LIMIT #{offset}, #{limit}" +
            "</script>")
    List<LfVehicleActivity> findAll(@Param("offset") int offset, @Param("limit") int limit, @Param("sort") Map<String, String> sort);


    @Insert({
            "<script>",
            "INSERT INTO lf_vehicle_activity " +
                    "    (registration_number, created_at, departed_time," +
                    "        arrival_time, origin, destination, total_running_in_minute, total_pause_in_minute, distance_with_gps," +
                    "        number_of_stop_start, max_speed, average_speed)" +
                    "VALUES" +
                    "    <foreach item='vehicleActivity' collection='lfVehicleActivityList' open='' separator=',' close=''>" +
                    "        (" +
                    "            #{vehicleActivity.registrationNumber}," +
                    "            #{vehicleActivity.createdAt}," +
                    "            #{vehicleActivity.departedTime}," +
                    "            #{vehicleActivity.arrivalTime}," +
                    "            #{vehicleActivity.origin}," +
                    "            #{vehicleActivity.destination}," +
                    "            #{vehicleActivity.totalRunningInMinute}," +
                    "            #{vehicleActivity.totalPauseInMinute}," +
                    "            #{vehicleActivity.distanceWithGps}," +
                    "            #{vehicleActivity.numberOfStopStart}," +
                    "            #{vehicleActivity.maxSpeed}," +
                    "            #{vehicleActivity.averageSpeed}" +
                    "        )" +
                    "    </foreach>",
            "</script>"})
    void insertBatch(@Param("lfVehicleActivityList") List<LfVehicleActivity> lfVehicleActivityList);

    @Delete("DELETE FROM lf_vehicle_activity")
    void deleteAll();

    @Delete("<script>" +
            "DELETE FROM lf_vehicle_activity " +
            "<if test='idList.size() > 0'>" +
                "WHERE id IN " +
                "<foreach item='entry' collection='idList' separator=',' open='(' close=')'>" +
                    "#{entry}" +
                "</foreach> " +
            "</if>" +
            "</script>")
    void deleteByIds(@Param("idList") List<Long> idList);
}
