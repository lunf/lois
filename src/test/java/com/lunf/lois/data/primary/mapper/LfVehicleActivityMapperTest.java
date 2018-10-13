package com.lunf.lois.data.primary.mapper;

import com.lunf.lois.config.PrimaryDatabaseConfig;
import com.lunf.lois.config.SecondaryDatabaseConfig;
import com.lunf.lois.data.TestDataConfig;
import com.lunf.lois.data.primary.entity.LfVehicleActivity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void insertBatchVehicleActivityTest() throws Exception {
        LfVehicleActivity lfVehicleAct = new LfVehicleActivity();

        lfVehicleAct.setRegistrationNumber(regNo);
        lfVehicleAct.setCreatedAt(ZonedDateTime.now());
        lfVehicleAct.setDepartedTime(LocalTime.of(6,52));
        lfVehicleAct.setArrivalTime(LocalTime.of(9,43));
        lfVehicleAct.setOrigin("TP. Hà Nội");
        lfVehicleAct.setDestination("TP. Hà Nội");
        lfVehicleAct.setTotalRunningInMinute(114);
        lfVehicleAct.setTotalPauseInMinute(1318);
        lfVehicleAct.setDistanceWithGps(81.57);
        lfVehicleAct.setNumberOfStopStart(19);
        lfVehicleAct.setMaxSpeed(81D);
        lfVehicleAct.setAverageSpeed(42D);

        Thread.sleep(100);

        LfVehicleActivity lfVehicleActivity = new LfVehicleActivity();

        lfVehicleActivity.setRegistrationNumber(regNo);
        lfVehicleActivity.setCreatedAt(ZonedDateTime.now());
        lfVehicleActivity.setDepartedTime(LocalTime.of(12,52));
        lfVehicleActivity.setArrivalTime(LocalTime.of(13,43));
        lfVehicleActivity.setOrigin("TP. Hà Nội");
        lfVehicleActivity.setDestination("TP. Hà Nội");
        lfVehicleActivity.setTotalRunningInMinute(114);
        lfVehicleActivity.setTotalPauseInMinute(1318);
        lfVehicleActivity.setDistanceWithGps(81.57);
        lfVehicleActivity.setNumberOfStopStart(19);
        lfVehicleActivity.setMaxSpeed(81D);
        lfVehicleActivity.setAverageSpeed(42D);

        List<LfVehicleActivity> list = new ArrayList<>();
        list.add(lfVehicleAct);
        list.add(lfVehicleActivity);

        lfVehicleActivityMapper.insertBatch(list);

        List<LfVehicleActivity> foundList = lfVehicleActivityMapper.findByRego(regNo);


        assertThat(foundList).isNotNull();
        assertThat(foundList.size()).isEqualTo(2);


    }
}
