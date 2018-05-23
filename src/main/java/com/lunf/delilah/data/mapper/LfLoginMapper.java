package com.lunf.delilah.data.mapper;

import com.lunf.delilah.data.entity.LfLogin;
import org.apache.ibatis.annotations.*;

import java.util.Collection;

@Mapper
public interface LfLoginMapper {

    @Insert("insert into lf_login (username, password_hash, user_id, login_type, token) " +
            "values (#{username}, #{passwordHash}, #{user.id}, #{loginType}, #{token})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    void insert(LfLogin lfLogin);

    @Select("select * from lf_login where username = #{username}")
    @Results({
            @Result(property = "user", column = "user_id", one = @One(select = "com.lunf.delilah.data.mapper.LfUserMapper.findById"))
    })
    Collection<LfLogin> findByUsername(@Param("username") String username);

    @Select("select * from lf_login where password_hash = #{passwordHash}")
    @Results({
            @Result(property = "user", column = "user_id", one = @One(select = "com.lunf.delilah.data.mapper.LfUserMapper.findById"))
    })
    LfLogin findByPasswordHash(@Param("passwordHash") String passwordHash);

    @Select("select * from lf_login where token = #{token}")
    @Results({
            @Result(property = "user", column = "user_id", one = @One(select = "com.lunf.delilah.data.mapper.LfUserMapper.findById"))
    })
    LfLogin findByToken(@Param("token") String token);

    @Delete("delete from lf_login where token = #{token}")
    void deleteByToken(@Param("token") String token);

    @Update("update lf_login set token = #{token} where username = #{username} and login_type = #{loginType}")
    void updateTokenByUsernameAndLoginType(@Param("token") String token, @Param("username") String username, @Param("loginType") int loginType);
}
