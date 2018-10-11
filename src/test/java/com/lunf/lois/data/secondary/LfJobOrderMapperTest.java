package com.lunf.lois.data.secondary;

import com.lunf.lois.config.PrimaryDatabaseConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import com.lunf.lois.config.SecondaryDatabaseConfig;
import com.lunf.lois.data.TestDataConfig;
import com.lunf.lois.data.secondary.entity.LfJobOrder;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@MybatisTest
@ContextConfiguration(classes = {TestDataConfig.class, PrimaryDatabaseConfig.class, SecondaryDatabaseConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LfJobOrderMapperTest {

    @Autowired
    private LfJobOrderMapper lfJobOrderMapper;

    @Test
    public void findLfOrderByOrderCodeTest() {
        LfJobOrder lfJobOrder = lfJobOrderMapper.findByOrderCode("GRD25093");

        assertThat(lfJobOrder).isNotNull();
        assertThat(lfJobOrder.getShipAddress()).isEqualTo("hcm");
        assertThat(lfJobOrder.getJobType()).isEqualTo(1);

    }

}
