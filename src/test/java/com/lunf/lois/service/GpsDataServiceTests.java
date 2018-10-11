package com.lunf.lois.service;

import com.lunf.lois.service.impl.GpsDataServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GpsDataServiceTests {

    private GpsDataService gpsDataService;

    @Before
    public void setup() {
        gpsDataService = new GpsDataServiceImpl();
    }

    @Test
    public void uploadGpsDataFileTest() throws Exception {

        File file = ResourceUtils.getFile("classpath:report_gps.xlsx");


        if (file == null || !file.canRead()) {
            throw new FileNotFoundException();
        }
        System.out.println("Can read " + file.canRead());
        gpsDataService.processDataImport(file);


    }
}
