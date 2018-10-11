package com.lunf.lois.data.primary.mapper;

import com.lunf.lois.config.PrimaryDatabaseConfig;
import com.lunf.lois.data.primary.entity.LfOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import com.lunf.lois.config.SecondaryDatabaseConfig;
import com.lunf.lois.data.TestDataConfig;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@MybatisTest
@Profile("test")
@ContextConfiguration(classes = {TestDataConfig.class, PrimaryDatabaseConfig.class, SecondaryDatabaseConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LfOrderMapperTest {

    @Autowired
    private LfOrderMapper lfOrderMapper;

    private String orderCode = "ORD-9999";
    private String address = "Trung Kính, Hà Nội";

    @Test
    public void insertOrderTest() {
        LfOrder lfOrder = new LfOrder();

        lfOrder.setBookingQuota(10);
        lfOrder.setCode(orderCode);
        lfOrder.setDeliveryAddress(address);
        lfOrder.setEstimatedCompletionDate(ZonedDateTime.now());
        lfOrder.setName("Van phong");
        lfOrder.setType(1);

        lfOrderMapper.insert(lfOrder);

        LfOrder foundOrder = lfOrderMapper.findById(lfOrder.getId());

        assertThat(foundOrder.getCode()).isEqualTo(orderCode);
        assertThat(foundOrder.getBookingQuota()).isEqualTo(10);
        assertThat(foundOrder.getDeliveryAddress()).isEqualTo(address);
    }
}
