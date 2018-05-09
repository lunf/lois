package com.lunf.delilah.data.mapper;

import com.lunf.delilah.data.entity.LfUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LfUserMapper {

    @Insert("insert into lf_user (username, first_name, last_name)" +
            " values (#{username}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys=true, keyColumn = "id")
    void insert(LfUser lfUser);

    @Select("select * from lf_user where username = #{username}")
    LfUser findByUsername(@Param("username") String username);


    @Select("select * from lf_user where id = #{id}")
    LfUser findById(@Param("id") Long id);
}
