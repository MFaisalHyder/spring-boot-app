package com.spring.project.itc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.project.config.BaseTest_WMT;
import com.spring.project.constant.ApplicationConstants;
import com.spring.project.controller.UserController;
import com.spring.project.dto.EmployeeDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

@WebMvcTest(value = UserController.class, secure = false)
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
    private ObjectMapper objectMapper;

    private static EmployeeDTO employee;

    private static final String EMIRATES_ID = "DXB_UAE_123457654321";
    private static final String FIRST_NAME = "Muhammad";
    private static final String LAST_NAME = "Hyder";
    private static final String STAFF_ID = "SE_007";
    private static final String PASSWORD = "12345ABCDEF";

    /**
     * We need to declare such methods as static if we are not using @TestInstance for Test class
     * But static should be avoided as it will not be cleaned up when all test cases are executed
     */
    @BeforeAll
    void beforeAll() {
        employee = new EmployeeDTO();
        employee.setEmiratesID(EMIRATES_ID);
        employee.setFirstName(FIRST_NAME);
        employee.setLastName(LAST_NAME);
        employee.setStaffID(STAFF_ID);
        employee.setPassword(PASSWORD);

    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUsersListControllerTest() throws Exception {
        mockMvc.perform(get("/user/listAll").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(ApplicationConstants.GeneralConstants.SUCCESS.getValue())))
                .andDo(print());

    }

    @Test
    void getUserByEmiratesIDControllerTest() throws Exception {
        String EMIRATES_ID = "ABCDEFT12345";

        mockMvc.perform(get("/user/emiratesID/" + EMIRATES_ID).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(EMIRATES_ID)))
                .andDo(print());

    }

    @Test
    void registerUserControllerTest() throws Exception {
        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}