package com.lunf.lois.data.primary.mapper;

import com.lunf.lois.data.primary.PrimaryMapper;
import com.lunf.lois.data.primary.entity.LfDevice;
import org.apache.ibatis.annotations.*;

@PrimaryMapper
public interface LfDeviceMapper {

    @Insert("insert into lf_device (created_at, notification_id, status, device_os, device_name, device_os_version, device_model)" +
            " values (#{createdAt}, #{notificationId}, #{status}, #{deviceOs}, #{deviceName}, #{deviceOsVersion}, #{deviceModel})")
    @Options(useGeneratedKeys=true, keyColumn = "id")
    void insert(LfDevice lfDevice);

    @Select("select * from lf_device where id = #{id}")
    LfDevice findById(@Param("id") Long id);

    @Select("select * from lf_device where notification_id = #{notificationId}")
    LfDevice findByNotificationId(@Param("notificationId") String notificationId);
}
