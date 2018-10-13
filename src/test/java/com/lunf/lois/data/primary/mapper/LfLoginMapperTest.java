package com.lunf.lois.data.primary.mapper;

import com.lunf.lois.config.PrimaryDatabaseConfig;
import com.lunf.lois.data.primary.entity.LfLogin;
import com.lunf.lois.data.primary.entity.LfUser;
import com.lunf.lois.utilities.RandomTokenGenerator;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import com.lunf.lois.config.SecondaryDatabaseConfig;
import com.lunf.lois.data.TestDataConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@MybatisTest
@Profile("test")
@ContextConfiguration(classes = {TestDataConfig.class, PrimaryDatabaseConfig.class, SecondaryDatabaseConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LfLoginMapperTest {

    @Autowired
    private LfLoginMapper lfLoginMapper;

    @Autowired
    private LfUserMapper lfUserMapper;

    private LfUser lfUser;

    private String randomPasswordHash;

    private RandomTokenGenerator randomTokenGenerator;

    @Before
    public void setup() {

        randomTokenGenerator = new RandomTokenGenerator();

        lfUser = new LfUser();

        lfUser.setLastName("Jame");
        lfUser.setFirstName("Doe");
        lfUser.setUsername("jame.doe@domain.com");

        lfUserMapper.insert(lfUser);

    }

    @Test
    public void insertLoginTest() {
        randomPasswordHash = randomTokenGenerator.nextString();

        LfLogin lfLogin = new LfLogin();
        lfLogin.setLoginType(1);
        lfLogin.setPasswordHash(randomPasswordHash);
        lfLogin.setUser(lfUser);
        lfLogin.setUsername(lfUser.getUsername());
        lfLogin.setToken(randomPasswordHash);

        lfLoginMapper.insert(lfLogin);

        Collection<LfLogin> loginCollection = lfLoginMapper.findByUsername(lfUser.getUsername());

        for (LfLogin foundLogin : loginCollection) {
            assertThat(foundLogin).isNotNull();
            assertThat(foundLogin.getUser().getUsername()).isEqualTo(lfUser.getUsername());
            assertThat(foundLogin.getPasswordHash()).isEqualTo(randomPasswordHash);
        }
    }

    @Test
    public void getLoginByUsernameTest() {
        Collection<LfLogin> lfLoginCollection = lfLoginMapper.findByUsername("john.doe@domain.com");

        for (LfLogin lfLogin : lfLoginCollection) {

            assertThat(lfLogin.getUsername()).isEqualTo("john.doe@domain.com");

            if (lfLogin.getLoginType() == 1) {
                assertThat(lfLogin.getPasswordHash()).isEqualTo("$2a$10$/7PScdp9I0tisbJckn3leunI58GZ1n.w5irYpG1ae/Vd4ljtXXCSe");
                assertThat(lfLogin.getToken()).isEqualTo("heR4rTcM2L8XfdSG9s6DaAN3YQuZxWVq");
            }

            assertThat(lfLogin.getUser().getFirstName()).isEqualTo("John");
            assertThat(lfLogin.getUser().getLastName()).isEqualTo("Doe");
        }

    }

    @Test
    public void getLoginByPasswordHashTest() {
        LfLogin lfLogin = lfLoginMapper.findByPasswordHash("$2a$10$/7PScdp9I0tisbJckn3leunI58GZ1n.w5irYpG1ae/Vd4ljtXXCSe");

        assertThat(lfLogin.getUsername()).isEqualTo("john.doe@domain.com");
        assertThat(lfLogin.getToken()).isEqualTo("heR4rTcM2L8XfdSG9s6DaAN3YQuZxWVq");
        assertThat(lfLogin.getUser().getFirstName()).isEqualTo("John");
        assertThat(lfLogin.getUser().getLastName()).isEqualTo("Doe");
    }

    @Test
    public void getLoginByTokenTest() {
        LfLogin lfLogin = lfLoginMapper.findByToken("UmfyL4aBCK11tNgEs5CcjC4kv31nFI6Q");

        assertThat(lfLogin.getUsername()).isEqualTo("john.doe@domain.com");
        assertThat(lfLogin.getPasswordHash()).isEqualTo("UmfyL4aBCK11tNgEs5CcjC4kv31nFI6Q");
        assertThat(lfLogin.getUser().getFirstName()).isEqualTo("John");
        assertThat(lfLogin.getUser().getLastName()).isEqualTo("Doe");
        assertThat(lfLogin.getLoginType()).isEqualTo(2);
    }

    @Test
    public void removeLoginTokenTest() {
        lfLoginMapper.deleteByToken("UmfyL4aBCK11tNgEs5CcjC4kv31nFI6Q");

        LfLogin lfLogin = lfLoginMapper.findByToken("UmfyL4aBCK11tNgEs5CcjC4kv31nFI6Q");

        assertThat(lfLogin).isNull();
    }

    @Test
    public void updateLoginTokenTest() {
        LfLogin lfLogin = lfLoginMapper.findByToken("UmfyL4aBCK11tNgEs5CcjC4kv31nFI6Q");

        assertThat(lfLogin).isNotNull();
        String token = new RandomTokenGenerator.Builder().nextString();

        lfLoginMapper.updateTokenByUsernameAndLoginType(token, lfLogin.getUsername(), lfLogin.getLoginType());

        LfLogin found = lfLoginMapper.findByToken(token);

        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo(lfLogin.getUsername());
    }
}
