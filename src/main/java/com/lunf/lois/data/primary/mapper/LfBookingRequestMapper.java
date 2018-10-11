package com.lunf.lois.data.primary.mapper;

import com.lunf.lois.data.primary.PrimaryMapper;
import org.apache.ibatis.annotations.*;
import com.lunf.lois.data.primary.entity.LfBookingRequest;

@PrimaryMapper
public interface LfBookingRequestMapper {

    @Insert("insert into lf_booking_request (from_user_id, request_vehicle_type, order_code, title, description, delivery_address, created_at, status, booked_for) " +
            "values (#{requester.id}, #{requestVehicleType}, #{orderCode}, #{title}, #{description}, #{deliveryAddress}, #{createdAt}, #{status}, #{bookedFor})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    void insert(LfBookingRequest lfBookingRequest);

    @Select("select * from lf_booking_request where id = #{id}")
    @Results({
            @Result(property = "requester", column = "from_user_id", one = @One(select = "LfUserMapper.findById"))
    })
    LfBookingRequest findById(@Param("id") Long id);
}
