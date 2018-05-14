package com.lunf.delilah.data.mapper;

import com.lunf.delilah.data.entity.LfLogin;
import com.lunf.delilah.data.entity.LfUser;
import com.lunf.delilah.utilities.RandomString;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@MybatisTest
public class LfLoginMapperTest {

    @Autowired
    private LfLoginMapper lfLoginMapper;

    @Autowired
    private LfUserMapper lfUserMapper;

    private LfUser lfUser;

    private String randomPasswordHash;

    private RandomString randomString;

    @Before
    public void setup() {

        randomString = new RandomString();

        lfUser = new LfUser();

        lfUser.setLastName("Jame");
        lfUser.setFirstName("Doe");
        lfUser.setUsername("jame.doe@domain.com");

        lfUserMapper.insert(lfUser);

    }

    @Test
    public void insertLoginTest() {
        randomPasswordHash = randomString.nextString();

        LfLogin lfLogin = new LfLogin();
        lfLogin.setLoginType(1);
        lfLogin.setPasswordHash(randomPasswordHash);
        lfLogin.setUser(lfUser);
        lfLogin.setUsername(lfUser.getUsername());

        lfLoginMapper.insert(lfLogin);

        LfLogin foundLogin = lfLoginMapper.findByUsername(lfUser.getUsername());

        assertThat(foundLogin).isNotNull();
        assertThat(foundLogin.getUser().getUsername()).isEqualTo(lfUser.getUsername());
        assertThat(foundLogin.getPasswordHash()).isEqualTo(randomPasswordHash);
    }

    @Test
    public void getLoginByUsernameTest() {
        LfLogin lfLogin = lfLoginMapper.findByUsername("john.doe@domain.com");

        assertThat(lfLogin.getUsername()).isEqualTo("john.doe@domain.com");
        assertThat(lfLogin.getPasswordHash()).isEqualTo("$2a$10$/7PScdp9I0tisbJckn3leunI58GZ1n.w5irYpG1ae/Vd4ljtXXCSe");
        assertThat(lfLogin.getLoginType()).isEqualTo(1);
        assertThat(lfLogin.getUser().getFirstName()).isEqualTo("John");
        assertThat(lfLogin.getUser().getLastName()).isEqualTo("Doe");

    }

    @Test
    public void getLoginByPasswordHashTest() {
        LfLogin lfLogin = lfLoginMapper.findByPasswordHash("UmfyL4aBCK11tNgEs5CcjC4kv31nFI6Q");

        assertThat(lfLogin.getUsername()).isEqualTo("john.doe@domain.com");
        assertThat(lfLogin.getPasswordHash()).isEqualTo("UmfyL4aBCK11tNgEs5CcjC4kv31nFI6Q");
        assertThat(lfLogin.getLoginType()).isEqualTo(2);
        assertThat(lfLogin.getUser().getFirstName()).isEqualTo("John");
        assertThat(lfLogin.getUser().getLastName()).isEqualTo("Doe");
    }
}
