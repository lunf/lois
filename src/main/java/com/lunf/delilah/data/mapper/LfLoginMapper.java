package com.lunf.delilah.data.mapper;

import com.lunf.delilah.data.entity.LfLogin;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LfLoginMapper {

    @Select("select * from lf_login where username = #{username} and login_type = 1")
    @Results({
            @Result(property = "user", column = "user_id", one=@One(select = "com.lunf.delilah.data.mapper.LfUserMapper.findById"))
    })
    LfLogin findByUsername(@Param("username") String username);

    @Select("select * from lf_login where password_hash = #{passwordHash} and login_type = 2")
    @Results({
            @Result(property = "user", column = "user_id", one=@One(select = "com.lunf.delilah.data.mapper.LfUserMapper.findById"))
    })
    LfLogin findByPasswordHash(@Param("passwordHash") String passwordHash);
}
