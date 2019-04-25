package com.spring.project.itc;

import com.spring.project.config.BaseTest_SBT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This example is done with @SpringBootTest. This is recommended way to carry out Integration Testing.
 * In integration testing we are running complete HTTP servers and testing our complete feature from controller to DB
 * i.e. tests are carried out with actual environment instead of mocks.
 */
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@DisplayName("UserControllerTest_SBT - SpringBootTest")
class UserControllerTest_SBT extends BaseTest_SBT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUsersList() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/listAll").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

}