package com.lunf.lois.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lunf.lois.LoisApplication;
import com.lunf.lois.config.SecurityConfig;
import com.lunf.lois.controller.request.RegisterUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMybatis
public class PublicUserControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private FilterChainProxy filterChainProxy;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .dispatchOptions(true)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void loginUserMissingParam() throws Exception {
        mockMvc.perform(post("/public/users/login"))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void registerUserSuccess() throws Exception {

        String username = "joy_4@domain.com";
        String password = "123456";

        RegisterUserRequest userRequest = new RegisterUserRequest();

        userRequest.setUsername(username);
        userRequest.setPassword(password);
        userRequest.setFirstName("Name");
        userRequest.setLastName("Now");


        String body = mapper.writeValueAsString(userRequest);

        mockMvc.perform(post("/public/users/register")
                .content(body)
                .contentType(contentType))
                .andDo(print()).andExpect(status().isCreated());
    }

    @Test
    public void loginUserSuccess() throws Exception {


        mockMvc.perform(post("/public/users/login")
                    .param("firstName", "")
                    .param("lastName", ""))
                .andDo(print()).andExpect(status().isBadRequest());
    }
}
