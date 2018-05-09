package com.lunf.delilah.data.mapper;

import com.lunf.delilah.data.entity.LfUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@MybatisTest
public class LfUserMapperTest {

    @Autowired
    private LfUserMapper lfUserMapper;

    @Test
    public void insertLfUserTest() {
        LfUser lfUser = new LfUser();
        lfUser.setUsername("jane.doe@domain.com");
        lfUser.setFirstName("Jane");
        lfUser.setLastName("Doe");

        lfUserMapper.insert(lfUser);

        LfUser foundUser = lfUserMapper.findById(lfUser.getId());


        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("jane.doe@domain.com");
    }

    @Test
    public void findLfUserByUsernameTest() {
        LfUser lfUser = lfUserMapper.findByUsername("john.doe@domain.com");

        assertThat(lfUser.getFirstName()).isEqualTo("John");
        assertThat(lfUser.getLastName()).isEqualTo("Doe");

    }

    @Test
    public void findLfUserByUsernameFailTest() {
        LfUser lfUser = lfUserMapper.findByUsername("john.doe@domain.com");

        assertThat(lfUser.getFirstName()).isNotEqualTo("John1");
        assertThat(lfUser.getLastName()).isNotEqualTo("Doe1");

    }

    @Test
    public void findLfUserByIdTest() {
        LfUser lfUser = lfUserMapper.findById(0L);
        assertThat(lfUser.getId()).isEqualTo(0L);
        assertThat(lfUser.getUsername()).isEqualTo("john.doe@domain.com");

    }
}
