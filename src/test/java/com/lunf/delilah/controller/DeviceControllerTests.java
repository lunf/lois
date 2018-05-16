package com.lunf.delilah.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest
public class DeviceControllerTests {

    @Autowired
    private DeviceController deviceController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contexLoads() throws Exception {
        assertThat(deviceController).isNotNull();
    }

    @Test
    public void shouldReturn201CodeTest() throws Exception {
        this.mockMvc.perform(post("/devices")).andDo(print()).andExpect(status().isCreated());
    }
}
