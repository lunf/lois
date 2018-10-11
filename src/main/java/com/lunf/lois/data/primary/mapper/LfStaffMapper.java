package com.lunf.lois.data.primary.mapper;

import com.lunf.lois.data.primary.PrimaryMapper;
import com.lunf.lois.data.primary.entity.LfStaff;
import org.apache.ibatis.annotations.*;

@PrimaryMapper
public interface LfStaffMapper {

    @Insert("insert into lf_staff (name, mobile_number, license_expired_date, driver_license_number, status) " +
            "values (#{name}, #{mobileNumber}, #{licenseExpiredDate}, #{driverLicenseNumber}, #{status})")
    @Options(useGeneratedKeys=true, keyColumn = "id")
    void insert(LfStaff lfStaff);

    @Select("select * from lf_staff where id = #{id}")
    LfStaff findById(@Param("id") Long id);

    @Select("select * from lf_staff where driverLicenseNumber = #{driverLicense}")
    LfStaff findByDriverLicense(@Param("driverLicense") String driverLicense);
}
