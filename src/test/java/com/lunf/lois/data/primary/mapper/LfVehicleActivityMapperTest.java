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
import com.lunf.lois.data.primary.entity.LfVehicleActivity;

import java.time.LocalTime;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@MybatisTest
@Profile("test")
@ContextConfiguration(classes = {TestDataConfig.class, PrimaryDatabaseConfig.class, SecondaryDatabaseConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LfVehicleActivityMapperTest {

    @Autowired
    private LfVehicleActivityMapper lfVehicleActivityMapper;

    private String regNo = "30A-5555";

    @Test
    public void insertVehicleActivityTest() {
        LfVehicleActivity lfVehicleActivity = new LfVehicleActivity();

        lfVehicleActivity.setRegistrationNumber(regNo);
        lfVehicleActivity.setCreatedAt(ZonedDateTime.now());
        lfVehicleActivity.setDepartedTime(LocalTime.of(6,52));
        lfVehicleActivity.setArrivalTime(LocalTime.of(9,43));
        lfVehicleActivity.setOrigin("TP. Hà Nội");
        lfVehicleActivity.setDestination("TP. Hà Nội");
        lfVehicleActivity.setTotalRunningInMinute(114);
        lfVehicleActivity.setTotalPauseInMinute(1318);
        lfVehicleActivity.setDistanceWithGps(81.57);
        lfVehicleActivity.setNumberOfStopStart(19);
        lfVehicleActivity.setMaxSpeed(81D);
        lfVehicleActivity.setAverageSpeed(42D);

        lfVehicleActivityMapper.insert(lfVehicleActivity);

        LfVehicleActivity foundVehicleActivity = lfVehicleActivityMapper.findById(lfVehicleActivity.getId());

        assertThat(foundVehicleActivity).isNotNull();
        assertThat(foundVehicleActivity.getRegistrationNumber()).isEqualTo(regNo);
        assertThat(foundVehicleActivity.getNumberOfStopStart()).isEqualTo(19);
        assertThat(foundVehicleActivity.getAverageSpeed()).isEqualTo(42D);


    }
}
