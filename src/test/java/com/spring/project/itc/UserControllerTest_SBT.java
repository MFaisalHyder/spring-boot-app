package com.spring.project.itc;

import com.spring.project.config.BaseTest_SBT;
import com.spring.project.constant.ApplicationConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This example is done with @SpringBootTest. This is recommended way to carry out Integration Testing.
 * In integration testing we are running complete HTTP servers and testing our complete feature from controller to DB
 * i.e. tests are carried out with actual environment instead of mocks.
 */
@EnableAutoConfiguration//(exclude = SecurityAutoConfiguration.class) //Now we don't need to skip Security
@DisplayName("UserControllerTest_SBT - SpringBootTest")
class UserControllerTest_SBT extends BaseTest_SBT {

    private final MockMvc mockMvc;

    @Autowired
    public UserControllerTest_SBT(final MockMvc mockMvc) {
        this.mockMvc = mockMvc;

    }

    @Test
        //@WithUserDetails("jionel.Pepsi@barka.com") //we can either annotate test case with a principal user or pass it in MockMvc
    void getUsersList() throws Exception {
        String USER = "jionel.Pepsi@barka.com";
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/listAll")
                .with(user(USER))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(ApplicationConstants.GeneralConstants.SUCCESS.getValue())))
                .andDo(print());

    }

}