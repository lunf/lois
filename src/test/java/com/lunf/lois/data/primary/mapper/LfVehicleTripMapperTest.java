package com.lunf.lois.data.primary.mapper;

import com.lunf.lois.config.PrimaryDatabaseConfig;
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
import com.lunf.lois.data.primary.entity.LfVehicleTrip;

import java.time.LocalTime;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@MybatisTest
@Profile("test")
@ContextConfiguration(classes = {TestDataConfig.class, PrimaryDatabaseConfig.class, SecondaryDatabaseConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LfVehicleTripMapperTest {
    @Autowired
    private LfVehicleTripMapper lfVehicleTripMapper;

    private String regNo = "30A-3456";

    @Test
    public void insertVehicleTripTest() {
        LfVehicleTrip lfVehicleTrip = new LfVehicleTrip();

        lfVehicleTrip.setRegistrationNumber(regNo);
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

        LfVehicleTrip foundVehicleTrip = lfVehicleTripMapper.findById(lfVehicleTrip.getId());

        assertThat(foundVehicleTrip).isNotNull();
        assertThat(foundVehicleTrip.getRegistrationNumber()).isEqualTo(regNo);
        assertThat(foundVehicleTrip.getDistanceWithMap()).isEqualTo(82D);
        assertThat(foundVehicleTrip.getAverageSpeed()).isEqualTo(42D);


    }
}
