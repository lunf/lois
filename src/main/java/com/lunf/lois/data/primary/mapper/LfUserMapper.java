package com.lunf.lois.data.primary.mapper;

import com.lunf.lois.data.primary.PrimaryMapper;
import com.lunf.lois.data.primary.entity.LfUser;
import org.apache.ibatis.annotations.*;

@PrimaryMapper
public interface LfUserMapper {

    @Insert("insert into lf_user (username, first_name, last_name)" +
            " values (#{username}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys=true, keyColumn = "id")
    void insert(LfUser lfUser);

    @Select("select * from lf_user where username = #{username}")
    LfUser findByUsername(@Param("username") String username);


    @Select("select * from lf_user where id = #{id}")
    LfUser findById(@Param("id") Long id);

    @Select("SELECT EXISTS(SELECT 1 FROM lf_user WHERE username=#{username})")
    boolean checkUserExists(@Param("username") String username);
}
