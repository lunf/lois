package com.lunf.lois.data.primary.mapper;

import com.lunf.lois.config.PrimaryDatabaseConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import com.lunf.lois.config.SecondaryDatabaseConfig;
import com.lunf.lois.data.TestDataConfig;
import com.lunf.lois.data.primary.entity.LfVehicle;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@MybatisTest
@Profile("test")
@ContextConfiguration(classes = {TestDataConfig.class, PrimaryDatabaseConfig.class, SecondaryDatabaseConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LfVehicleMapperTest {

    @Autowired
    private LfVehicleMapper lfVehicleMapper;

    LfVehicle defaultVehicle;
    String regNo = "30U-555.11";

    @Before
    public void setup() {
        defaultVehicle = new LfVehicle();
        defaultVehicle.setCreatedAt(ZonedDateTime.now());
        defaultVehicle.setMaker("FORD");
        defaultVehicle.setManufactureYear(2011);
        defaultVehicle.setModel("Ranger");
        defaultVehicle.setOdometer(30232);
        defaultVehicle.setPurchasedDate(ZonedDateTime.of(2015, 11, 23, 0, 0, 0, 0, ZoneId.systemDefault()));
        defaultVehicle.setStatus(1);
        defaultVehicle.setType(1);
        defaultVehicle.setRegistrationNumber(regNo);
    }

    @Test
    public void insertLfVehicleTest() {


        lfVehicleMapper.insert(defaultVehicle);

        LfVehicle foundMessage = lfVehicleMapper.findById(defaultVehicle.getId());

        assertThat(foundMessage).isNotNull();
        assertThat(foundMessage.getRegistrationNumber()).isEqualTo(regNo);
        assertThat(foundMessage.getManufactureYear()).isEqualTo(2011);
    }

    @Test(expected = DuplicateKeyException.class)
    public void checkRegNoUniqueTest() {
        lfVehicleMapper.insert(defaultVehicle);

        LfVehicle secondVehicle = new LfVehicle();

        secondVehicle.setCreatedAt(ZonedDateTime.now());
        secondVehicle.setMaker("Toyota");
        secondVehicle.setManufactureYear(2018);
        secondVehicle.setModel("Camry");
        secondVehicle.setOdometer(30232);
        secondVehicle.setPurchasedDate(ZonedDateTime.of(2018, 11, 23, 0, 0, 0, 0, ZoneId.systemDefault()));
        secondVehicle.setStatus(1);
        secondVehicle.setType(1);
        secondVehicle.setRegistrationNumber(regNo);


        lfVehicleMapper.insert(secondVehicle);


    }
}
