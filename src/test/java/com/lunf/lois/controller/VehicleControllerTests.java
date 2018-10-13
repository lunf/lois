package com.lunf.lois.controller;

import com.lunf.lois.service.VehicleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMybatis
public class VehicleControllerTests {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturn400CodeTest() throws Exception {
        mockMvc.perform(post("/devices").contentType(contentType))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void uploadVehicleFailTest() throws Exception {
        final InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.png");

        final MockMultipartFile data = new MockMultipartFile("file", "not_exist.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", input);

        mockMvc.perform(multipart("/vehicles/upload").file(data))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void uploadVehicleSuccessTest() throws Exception {


        File file = ResourceUtils.getFile("classpath:report_gps.xlsx");


        if (file == null || !file.canRead()) {
            fail("Fail not found");
        }

        FileInputStream input = new FileInputStream(file);

        final MockMultipartFile data = new MockMultipartFile("file", "report_gps.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", input);

        mockMvc.perform(multipart("/vehicles/upload").file(data))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

    }


    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
