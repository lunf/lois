package com.lunf.lois.data.primary.mapper;

import com.lunf.lois.data.primary.PrimaryMapper;
import com.lunf.lois.data.primary.entity.LfVehicle;
import org.apache.ibatis.annotations.*;

@PrimaryMapper
public interface LfVehicleMapper {

    @Insert("insert into lf_vehicle (created_at, registration_number, purchased_date, maker, model, manufacture_year, type, odometer, status) " +
            "values (#{createdAt}, #{registrationNumber}, #{purchasedDate}, #{maker}, #{model}, #{manufactureYear}, #{type}, #{odometer}, #{status})")
    @Options(useGeneratedKeys=true, keyColumn = "id")
    void insert(LfVehicle lfVehicle);

    @Select("select * from lf_vehicle where id = #{id}")
    LfVehicle findById(@Param("id") Long id);

    @Select("select * from lf_vehicle where registration_number = #{registrationNumber}")
    LfVehicle findByRegistrationNumber(@Param("registrationNumber") String registrationNumber);
}
