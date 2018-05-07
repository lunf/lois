package com.lunf.delilah.data.mapper;

import com.lunf.delilah.data.entity.LfUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LfUserMapper {

    @Select("select * from lf_user where username = #{username}")
    @Results({
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name")
    })
    LfUser findByUsername(@Param("username") String username);
}
