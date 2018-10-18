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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Test
    public void findAllPagingTest() throws Exception {

        lfVehicleActivityMapper.deleteAll();


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
        lfVehicleActivity.setCreatedAt(ZonedDateTime.now().minusDays(1));
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

        Map<String, String> sorts = new HashMap<>();


        List<LfVehicleActivity> firstList = lfVehicleActivityMapper.findAll(0,2, sorts);


        assertThat(firstList).isNotNull();
        assertThat(firstList.size()).isEqualTo(2);

        List<LfVehicleActivity> secondList = lfVehicleActivityMapper.findAll(1,2,sorts);

        assertThat(secondList).isNotNull();
        assertThat(secondList.size()).isEqualTo(1);

        // Last item of the first list == single item of the second
        assertThat(firstList.get(firstList.size() -1).getId()).isEqualTo(secondList.get(0).getId());


    }

    @Test
    public void findAllPagingSortByTest() throws Exception {

        String firstRego = "1";

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

        lfVehicleActivity.setRegistrationNumber(firstRego);
        lfVehicleActivity.setCreatedAt(ZonedDateTime.now().minusDays(1));
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

        Map<String, String> sorts = new HashMap<>();
        sorts.put("registration_number","ASC");


        List<LfVehicleActivity> foundList = lfVehicleActivityMapper.findAll(0,100, sorts);

        assertThat(foundList).isNotNull();
        assertThat(foundList.size()).isGreaterThanOrEqualTo(2);
        assertThat(foundList.get(0).getRegistrationNumber()).isEqualTo(firstRego);


        sorts.clear();
        sorts.put("registration_number","DESC");
        foundList = lfVehicleActivityMapper.findAll(0,100, sorts);

        assertThat(foundList).isNotNull();
        assertThat(foundList.size()).isGreaterThanOrEqualTo(2);
        assertThat(foundList.get(foundList.size() - 1).getRegistrationNumber()).isEqualTo(firstRego);

    }

    @Test
    public void findAllPagingMultipleSortByTest() throws Exception {

        lfVehicleActivityMapper.deleteAll();

        String firstRego = "1";

        ZonedDateTime now = ZonedDateTime.now();
        LfVehicleActivity lfVehicleAct = new LfVehicleActivity();

        lfVehicleAct.setRegistrationNumber(firstRego);
        lfVehicleAct.setCreatedAt(now);
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
        lfVehicleActivity.setCreatedAt(now.minusDays(1));
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

        LfVehicleActivity third = new LfVehicleActivity();

        third.setRegistrationNumber(regNo);
        third.setCreatedAt(now.minusDays(3));
        third.setDepartedTime(LocalTime.of(12,52));
        third.setArrivalTime(LocalTime.of(13,43));
        third.setOrigin("TP. Hà Nội");
        third.setDestination("TP. Hà Nội");
        third.setTotalRunningInMinute(114);
        third.setTotalPauseInMinute(1318);
        third.setDistanceWithGps(81.57);
        third.setNumberOfStopStart(19);
        third.setMaxSpeed(81D);
        third.setAverageSpeed(42D);

        List<LfVehicleActivity> list = new ArrayList<>();
        list.add(lfVehicleAct);
        list.add(lfVehicleActivity);
        list.add(third);

        lfVehicleActivityMapper.insertBatch(list);

        Map<String, String> sorts = new HashMap<>();
        sorts.put("registration_number","ASC");
        sorts.put("created_at","ASC");


        List<LfVehicleActivity> foundList = lfVehicleActivityMapper.findAll(0,100, sorts);

        for(LfVehicleActivity data: foundList) {
            System.out.println(data.getRegistrationNumber() + " " +data.getCreatedAt());
        }
        assertThat(foundList).isNotNull();
        assertThat(foundList.size()).isEqualTo(3);
        assertThat(foundList.get(0).getRegistrationNumber()).isEqualTo(firstRego);


        sorts.clear();
        sorts.put("registration_number","ASC");
        sorts.put("created_at","DESC");
        foundList = lfVehicleActivityMapper.findAll(0,100, sorts);

        for(LfVehicleActivity data: foundList) {
            System.out.println(data.getRegistrationNumber() + " " +data.getCreatedAt());
        }

        assertThat(foundList).isNotNull();
        assertThat(foundList.size()).isGreaterThanOrEqualTo(3);
        assertThat(foundList.get(foundList.size() - 1).getCreatedAt())
                .isBefore(foundList.get(foundList.size() - 2).getCreatedAt());

    }
}
