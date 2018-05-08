package com.lunf.delilah.data.mapper;

import com.lunf.delilah.data.entity.LfLogin;
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
