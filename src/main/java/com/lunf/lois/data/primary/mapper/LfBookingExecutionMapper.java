package com.lunf.lois.data.primary.mapper;

import com.lunf.lois.data.primary.PrimaryMapper;
import com.lunf.lois.data.primary.entity.LfBookingExecution;
import org.apache.ibatis.annotations.*;

@PrimaryMapper
public interface LfBookingExecutionMapper {

    @Insert("insert into lf_booking_execution (driver_id, booking_request_id, vehicle_trip_id) " +
            "values (#{driver.id}, #{bookingRequest.id}, #{vehicleTrip.id})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    void insert(LfBookingExecution lfBookingExecution);

    @Select("select * from lf_booking_execution where id = #{id}")
    @Results({
            @Result(property = "driver", column = "driver_id", one = @One(select = "LfStaffMapper.findById")),
            @Result(property = "bookingRequest", column = "booking_request_id", one = @One(select = "LfBookingRequestMapper.findById")),
            @Result(property = "vehicleTrip", column = "vehicle_trip_id", one = @One(select = "LfVehicleTripMapper.findById"))
    })
    LfBookingExecution findById(@Param("id") Long id);
}
