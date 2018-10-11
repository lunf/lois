package com.lunf.lois.data.primary.mapper;

import com.lunf.lois.config.PrimaryDatabaseConfig;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import com.lunf.lois.config.SecondaryDatabaseConfig;
import com.lunf.lois.data.TestDataConfig;
import com.lunf.lois.data.primary.entity.LfStaff;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@MybatisTest
@Profile("test")
@ContextConfiguration(classes = {TestDataConfig.class, PrimaryDatabaseConfig.class, SecondaryDatabaseConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LfStaffMapperTest {

    @Autowired
    private LfStaffMapper lfStaffMapper;

    @Before
    public void setup() {

    }

    @Test
    public void insertDriverTest() {

        String licenseNo = "YEO-12312312";

        LfStaff lfStaff = new LfStaff();
        lfStaff.setDriverLicenseNumber(licenseNo);
        lfStaff.setLicenseExpiredDate(ZonedDateTime.now());
        lfStaff.setMobileNumber("123456");
        lfStaff.setName("John");
        lfStaff.setStatus(1);



        lfStaffMapper.insert(lfStaff);
        LfStaff foundJob = lfStaffMapper.findById(lfStaff.getId());

        assertThat(foundJob).isNotNull();
        assertThat(foundJob.getDriverLicenseNumber()).isEqualTo(licenseNo);
    }

    @Test(expected = DuplicateKeyException.class)
    public void insertDuplicateLicenseTest() {
        String licenseNo = "YEO-12312312";

        LfStaff lfStaff = new LfStaff();
        lfStaff.setDriverLicenseNumber(licenseNo);
        lfStaff.setLicenseExpiredDate(ZonedDateTime.now());
        lfStaff.setMobileNumber("123456");
        lfStaff.setName("John");
        lfStaff.setStatus(1);

        lfStaffMapper.insert(lfStaff);

        LfStaff duplicate = new LfStaff();
        duplicate.setDriverLicenseNumber(licenseNo);
        duplicate.setLicenseExpiredDate(ZonedDateTime.now());
        duplicate.setMobileNumber("98732423");
        duplicate.setName("Jane");
        duplicate.setStatus(1);

        lfStaffMapper.insert(duplicate);
    }
}
