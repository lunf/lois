package com.lunf.lois.service;

import com.lunf.lois.service.impl.ExcelDataServiceImpl;
import com.lunf.lois.service.model.VehicleActivityDTO;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class ExcelDataServiceTests {

    @Autowired
    private ExcelDataService excelDataService;

    @TestConfiguration
    static class ExcelDataServiceTestsContextConfiguration {

        @Bean
        public ExcelDataService excelDataService() {
            return new ExcelDataServiceImpl();
        }
    }

    @Before
    public void setup() {

    }

    @Test
    public void uploadGpsDataFileTest() throws Exception {

        File file = ResourceUtils.getFile("classpath:report_gps.xlsx");


        if (file == null || !file.canRead()) {
            throw new FileNotFoundException();
        }

        FileInputStream input = new FileInputStream(file);

        MultipartFile multipartFile = new MockMultipartFile("report_gps.xlsx",
                file.getName(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", IOUtils.toByteArray(input));


        System.out.println("Can read " + file.canRead());
        List<VehicleActivityDTO> activityDTOList = excelDataService.processVehicleData(multipartFile);

        assertThat(activityDTOList).isNotNull();
        assertThat(activityDTOList.size()).isGreaterThan(1);


    }
}
