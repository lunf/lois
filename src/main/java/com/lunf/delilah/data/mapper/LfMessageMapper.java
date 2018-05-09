package com.lunf.delilah.data.mapper;

import com.lunf.delilah.data.entity.LfMessage;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LfMessageMapper {

    @Insert("insert into lf_message (job_id, device_id, notification_id, message_type, message_title, message_body, message_metadata) " +
            "values (#{job.id}, #{device.id}, #{notificationId}, #{messageType}, #{messageTitle}, #{messageBody}, #{messageMetadata})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    void insert(LfMessage lfMessage);

    @Select("select * from lf_message where id = #{id}")
    @Results({
            @Result(property = "job", column = "job_id", one=@One(select = "com.lunf.delilah.data.mapper.LfJobMapper.findById")),
            @Result(property = "device", column = "device_id", one=@One(select = "com.lunf.delilah.data.mapper.LfDeviceMapper.findById"))
    })
    LfMessage findById(@Param("id") Long id);
}
