package com.lunf.lois.data.primary.mapper;

import com.lunf.lois.config.PrimaryDatabaseConfig;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import com.lunf.lois.config.SecondaryDatabaseConfig;
import com.lunf.lois.data.TestDataConfig;
import com.lunf.lois.data.primary.entity.LfUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@MybatisTest
@Profile("test")
@ContextConfiguration(classes = {TestDataConfig.class, PrimaryDatabaseConfig.class, SecondaryDatabaseConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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

        LfUser lfUser = new LfUser();
        lfUser.setUsername("jane.doe@domain.com");
        lfUser.setFirstName("Jane");
        lfUser.setLastName("Doe");

        lfUserMapper.insert(lfUser);

        LfUser insertUser = lfUserMapper.findById(lfUser.getId());


        LfUser foundUser = lfUserMapper.findById(insertUser.getId());
        assertThat(foundUser.getId()).isEqualTo(lfUser.getId());
        assertThat(foundUser.getUsername()).isEqualTo("jane.doe@domain.com");

    }
}
