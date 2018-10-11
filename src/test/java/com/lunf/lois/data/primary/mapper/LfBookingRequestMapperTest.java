package com.lunf.lois.data.primary.mapper;

import com.lunf.lois.config.PrimaryDatabaseConfig;
import org.junit.Before;
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
import com.lunf.lois.data.primary.entity.LfBookingRequest;
import com.lunf.lois.data.primary.entity.LfUser;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@MybatisTest
@Profile("test")
@ContextConfiguration(classes = {TestDataConfig.class, PrimaryDatabaseConfig.class, SecondaryDatabaseConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LfBookingRequestMapperTest {

    @Autowired
    private LfBookingRequestMapper lfBookingRequestMapper;

    @Autowired
    private LfUserMapper lfUserMapper;

    private LfUser lfUser;

    private String orderNo = "ORD-00000";
    private String title = "Gia hàng";
    private String email = "jane.doe@domain.com";

    @Before
    public void setup() {



        lfUser = new LfUser();

        lfUser.setLastName("Jame");
        lfUser.setFirstName("Doe");
        lfUser.setUsername(email);

        lfUserMapper.insert(lfUser);

    }

    @Test
    public void insertBookingRequestTest() {

        LfBookingRequest lfBookingRequest = new LfBookingRequest();
        lfBookingRequest.setBookedFor(ZonedDateTime.now());
        lfBookingRequest.setCreatedAt(ZonedDateTime.now());
        lfBookingRequest.setDeliveryAddress("Mỹ Đình, Hà Nội");
        lfBookingRequest.setDescription("Long text description");
        lfBookingRequest.setOrderCode(orderNo);
        lfBookingRequest.setRequester(lfUser);
        lfBookingRequest.setRequestVehicleType(1);
        lfBookingRequest.setStatus(1);
        lfBookingRequest.setTitle(title);

        lfBookingRequestMapper.insert(lfBookingRequest);

        LfBookingRequest bookingRequest = lfBookingRequestMapper.findById(lfBookingRequest.getId());

        assertThat(bookingRequest.getOrderCode()).isEqualTo(orderNo);
        assertThat(bookingRequest.getStatus()).isEqualTo(1);
        assertThat(bookingRequest.getTitle()).isEqualTo(title);
        assertThat(bookingRequest.getRequester().getUsername()).isEqualTo(email);

    }
}
