package com.lunf.delilah.data.mapper;

import com.lunf.delilah.data.entity.LfJob;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LfJobMapper {

    @Insert("insert into lf_job (created_at, processing_at, completed_at, sender_username, message_type, message_title, message_body, message_metadata, send_to_type, devices) " +
            "values (#{createdAt}, #{processingAt}, #{completedAt}, #{sender.username}, #{messageType}, #{messageTitle}, #{messageBody}, #{messageMetadata}, #{sendToType}, #{devices})")
    @Options(useGeneratedKeys=true, keyColumn = "id")
    void insert(LfJob lfJob);

    @Select("select * from lf_job where id = #{id}")
    @Results({
            @Result(property = "sender", column = "sender_username", one=@One(select = "com.lunf.delilah.data.mapper.LfUserMapper.findByUsername"))
    })
    LfJob findById(@Param("id") Long id);
}
