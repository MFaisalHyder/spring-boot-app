package com.spring.project.itc;

import com.spring.project.config.BaseTest_WMT;
import com.spring.project.constant.ApplicationConstants;
import com.spring.project.controller.UserController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This example is done with @WebMvcTest. If we don't want complete Spring context to load, then just to
 * test controllers we can proceed this way. One thing is important here, since this is not 'Boot' so all
 * the required beans have to be provided through @Configuration.
 * <p>
 * Note: If test unable to find Controller, and gives 404, given project structure is as per guideline, then make sure
 * Configuration is done properly, ComponentScan, JpaRepositories, Beans, and DataSource.</p>
 */

@WebMvcTest(value = UserController.class)
@DisplayName("UserControllerTest_WMT - WebMVCTest")
class UserControllerTest_WMT extends BaseTest_WMT {
    /*
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    void setup() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();

        //This is for just loading a single controller class
        //this.mockMvc = standaloneSetup(UserController.class).build();
    }
    */

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUsersListTest() throws Exception {
        mockMvc.perform(get("/user/listAll").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(ApplicationConstants.GeneralConstants.SUCCESS.getValue())))
                .andDo(print());

    }

    @Test
    void getUserByEmiratesIDTest() throws Exception {
        mockMvc.perform(get("/user/ABCDEF12345").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ABCDEF12345")))
                .andDo(print());

    }
}