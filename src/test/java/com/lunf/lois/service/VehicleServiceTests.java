package com.lunf.lois.service;

import com.lunf.lois.config.PrimaryDatabaseConfig;
import com.lunf.lois.config.SecondaryDatabaseConfig;
import com.lunf.lois.data.TestDataConfig;
import com.lunf.lois.data.primary.mapper.LfVehicleActivityMapper;
import com.lunf.lois.service.exception.DelilahException;
import com.lunf.lois.service.impl.ExcelDataServiceImpl;
import com.lunf.lois.service.impl.VehicleServiceImpl;
import com.lunf.lois.service.model.VehicleActivityDTO;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@MybatisTest
@ContextConfiguration(classes = {TestDataConfig.class, PrimaryDatabaseConfig.class, SecondaryDatabaseConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VehicleServiceTests {

    @Autowired
    private ExcelDataService excelDataService;


    private VehicleService vehicleService;


    @MockBean
    private LfVehicleActivityMapper lfVehicleActivityMapper;


    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public ExcelDataService vehicleService() {
            return new ExcelDataServiceImpl();
        }
    }


    @Before
    public void setup() {
        vehicleService = new VehicleServiceImpl(lfVehicleActivityMapper, excelDataService);
    }


    @Test
    public void uploadVehicleActivityFileTest() {

        try {
            File file = ResourceUtils.getFile("classpath:report_gps.xlsx");


            if (file == null || !file.canRead()) {
                fail("Fail not found");
            }

            FileInputStream input = new FileInputStream(file);

            MultipartFile multipartFile = new MockMultipartFile("report_gps.xlsx",
                    file.getName(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", IOUtils.toByteArray(input));


            System.out.println("Can read " + file.canRead());
            try {
                vehicleService.uploadVehicleActivityFile(multipartFile);
            } catch (DelilahException ex) {
                fail(ex.getMessage());
            }
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void getVehicelActivityPagingTest() {
        SpringDataWebProperties.Pageable pageable = new SpringDataWebProperties.Pageable();

        try {

            File file = ResourceUtils.getFile("classpath:report_gps.xlsx");


            if (file == null || !file.canRead()) {
                fail("Fail not found");
            }

            FileInputStream input = new FileInputStream(file);

            MultipartFile multipartFile = new MockMultipartFile("report_gps.xlsx",
                    file.getName(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", IOUtils.toByteArray(input));


            vehicleService.uploadVehicleActivityFile(multipartFile);

            List<VehicleActivityDTO> activityDTOList = vehicleService.findRawVehicleReportPaginated(0, 10, new HashMap<>());

            assertThat(activityDTOList).isNotNull();
            assertThat(activityDTOList.size()).isEqualTo(1);

        } catch (Exception ex) {
            fail(ex.getMessage());
        }

    }
}
