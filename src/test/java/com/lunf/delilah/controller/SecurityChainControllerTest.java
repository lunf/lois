package com.lunf.delilah.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lunf.delilah.controller.request.DeviceRequest;
import com.lunf.delilah.service.DeviceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.time.ZonedDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMybatis
public class SecurityChainControllerTest {
    ObjectMapper mapper = new ObjectMapper();
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    private MockMvc mockMvc;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private FilterChainProxy filterChainProxy;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(filterChainProxy).build();
    }

    @Test
    public void shouldReturn401CodeTest() throws Exception {
        mockMvc.perform(post("/devices").contentType(contentType))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    public void passSecurityCheck() throws Exception {
        mockMvc.perform(post("/devices")
                .header(HttpHeaders.AUTHORIZATION, "Bearer UmfyL4aBCK11tNgEs5CcjC4kv31nFI6Q")
                .contentType(contentType))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void passSecurityAndCreateDevice() throws Exception {

        String notificationId = "something_should_be_unique";
        ZonedDateTime now = ZonedDateTime.now();
        String year = String.valueOf(now.getYear());

        DeviceRequest deviceRequest = new DeviceRequest();
        deviceRequest.setNotification_id(notificationId);

        String jsonInString = mapper.writeValueAsString(deviceRequest);

        mockMvc.perform(post("/devices")
                .header(HttpHeaders.AUTHORIZATION, "Bearer UmfyL4aBCK11tNgEs5CcjC4kv31nFI6Q")
                .contentType(contentType)
                .content(jsonInString))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.notification_id", is(notificationId)))
                .andExpect(jsonPath("$.created_at", containsString(year)));

    }

}
