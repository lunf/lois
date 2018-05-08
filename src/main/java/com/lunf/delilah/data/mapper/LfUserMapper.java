package com.lunf.delilah.data.mapper;

import com.lunf.delilah.data.entity.LfUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LfUserMapper {

    @Select("select * from lf_user where username = #{username}")
    LfUser findByUsername(@Param("username") String username);


    @Select("select * from lf_user where id = #{id}")
    LfUser findUserById(@Param("id") Long id);
}
