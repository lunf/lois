package com.lunf.lois.data.primary.mapper;

import com.lunf.lois.config.PrimaryDatabaseConfig;
import com.lunf.lois.data.primary.entity.*;
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

import java.time.LocalTime;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@MybatisTest
@Profile("test")
@ContextConfiguration(classes = {TestDataConfig.class, PrimaryDatabaseConfig.class, SecondaryDatabaseConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LfBookingExecutionMapperTest {

    @Autowired
    private LfStaffMapper lfStaffMapper;

    @Autowired
    private LfUserMapper lfUserMapper;

    @Autowired
    private LfBookingRequestMapper lfBookingRequestMapper;

    @Autowired
    private LfVehicleTripMapper lfVehicleTripMapper;

    @Autowired
    private LfBookingExecutionMapper lfBookingExecutionMapper;

    private LfStaff lfStaff;
    private LfUser lfUser;
    private LfBookingRequest lfBookingRequest;
    private LfVehicleTrip lfVehicleTrip;

    private String orderNo = "ORD-00000";
    private String title = "Gia hàng";
    private String email = "jane.doe@domain.com";
    private String licenseNo = "23C-555.55";
    @Before
    public void setup() {

        lfStaff = new LfStaff();
        lfStaff.setDriverLicenseNumber(licenseNo);
        lfStaff.setLicenseExpiredDate(ZonedDateTime.now());
        lfStaff.setMobileNumber("123456");
        lfStaff.setName("John");
        lfStaff.setStatus(1);

        lfStaffMapper.insert(lfStaff);

        lfUser = new LfUser();
        lfUser.setUsername("jane.doe@domain.com");
        lfUser.setFirstName("Jane");
        lfUser.setLastName("Doe");

        lfUserMapper.insert(lfUser);

        lfBookingRequest = new LfBookingRequest();
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

        lfVehicleTrip = new LfVehicleTrip();

        lfVehicleTrip.setRegistrationNumber(licenseNo);
        lfVehicleTrip.setCreatedAt(ZonedDateTime.now());
        lfVehicleTrip.setDepartedTime(LocalTime.of(6,52));
        lfVehicleTrip.setArrivalTime(LocalTime.of(9,43));
        lfVehicleTrip.setOrigin("TP. Hà Nội");
        lfVehicleTrip.setDestination("TP. Hà Nội");
        lfVehicleTrip.setTotalRunningInMinute(114);
        lfVehicleTrip.setTotalPauseInMinute(1318);
        lfVehicleTrip.setDistanceWithGps(81.57);
        lfVehicleTrip.setDistanceWithMap(82D);
        lfVehicleTrip.setNumberOfStopStart(19);
        lfVehicleTrip.setMaxSpeed(81D);
        lfVehicleTrip.setAverageSpeed(42D);

        lfVehicleTripMapper.insert(lfVehicleTrip);


    }

    @Test
    public void insertBookingExecutionTest() {
        LfBookingExecution lfBookingExecution = new LfBookingExecution();

        lfBookingExecution.setDriver(lfStaff);
        lfBookingExecution.setBookingRequest(lfBookingRequest);
        lfBookingExecution.setVehicleTrip(lfVehicleTrip);

        lfBookingExecutionMapper.insert(lfBookingExecution);

        LfBookingExecution foundExecution = lfBookingExecutionMapper.findById(lfBookingExecution.getId());

        assertThat(foundExecution).isNotNull();
        assertThat(foundExecution.getDriver().getDriverLicenseNumber()).isEqualTo(licenseNo);
        assertThat(foundExecution.getBookingRequest().getOrderCode()).isEqualTo(orderNo);
        assertThat(foundExecution.getVehicleTrip().getRegistrationNumber()).isEqualTo(licenseNo);

    }
}
