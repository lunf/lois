package com.lunf.lois.controller;

import com.lunf.lois.controller.request.DeviceRequest;
import com.lunf.lois.service.DeviceService;
import com.lunf.lois.service.constant.ErrorCode;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.ZonedDateTime;
import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMybatis
public class DeviceControllerTests {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private DeviceService deviceService;

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
    public void createDeviceFailValidationTest() throws Exception {
        DeviceRequest deviceRequest = new DeviceRequest();

        mockMvc.perform(post("/devices").contentType(contentType).content(this.json(deviceRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(ErrorCode.FAIL_VALIDATION.name())));

    }

    @Test
    public void createDeviceSuccessTest() throws Exception {

        String notificationId = "something_should_be_unique";
        ZonedDateTime now = ZonedDateTime.now();
        String year = String.valueOf(now.getYear());

        DeviceRequest deviceRequest = new DeviceRequest();
        deviceRequest.setNotification_id(notificationId);

        mockMvc.perform(post("/devices").contentType(contentType).content(this.json(deviceRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.notification_id", is(notificationId)))
                .andExpect(jsonPath("$.created_at", containsString(year)));
    }

    @Test
    public void createDeviceDuplicateTest() throws Exception {

        String notificationId = "something_should_be_unique_data";
        DeviceRequest deviceRequest = new DeviceRequest();
        deviceRequest.setNotification_id(notificationId);

        mockMvc.perform(post("/devices").contentType(contentType).content(this.json(deviceRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.notification_id", is(notificationId)));

        mockMvc.perform(post("/devices").contentType(contentType).content(this.json(deviceRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(ErrorCode.DUPLICATE.name())));

    }


    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
