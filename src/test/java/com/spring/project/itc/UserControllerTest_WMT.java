package com.spring.project.itc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.project.config.BaseTest_WMT;
import com.spring.project.constant.ApplicationConstants;
import com.spring.project.controller.UserController;
import com.spring.project.dto.RoleDTO;
import com.spring.project.dto.UserDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

//@WebMvcTest(value = UserController.class, secure = false) // to bypass spring security we need to mark secure = false
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

    private final MockMvc mockMvc;

    @Autowired
    public UserControllerTest_WMT(final MockMvc mockMvc) {
        this.mockMvc = mockMvc;

    }

    private static final Long ADMIN_ROLE_ID = -200L;
    private static final String EMIRATES_ID = "DXB_UAE_123457654321";
    private static final String FIRST_NAME = "Muhammad";
    private static final String LAST_NAME = "Hyder";
    private static final String EMAIL = "def@ghi.com";
    private static final String STAFF_ID = "SE_007";
    private static final String PASSWORD = "12345ABCDEF";

    /**
     * We need to declare such methods as static if we are not using @TestInstance for Test class
     * But static should be avoided as it will not be cleaned up when all test cases are executed
     */
    @BeforeAll
    void beforeAll() {
        RoleDTO role = new RoleDTO();
        role.setID(ADMIN_ROLE_ID);

        UserDTO user = new UserDTO();
        user.setEmiratesID(EMIRATES_ID);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setEmail(EMAIL);
        user.setStaffID(STAFF_ID);
        user.setPassword(PASSWORD);
        user.setRole(role);

    }

    /**
     * Principal User : "john.Smith@gmail.com"
     *
     * @throws Exception in case of any interruption
     */
    @Test
    @WithUserDetails("john.Smith@gmail.com")
    void getUsersListControllerTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/user/listAll").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(ApplicationConstants.GeneralConstants.SUCCESS.getValue())))
                .andDo(print())
                .andReturn();

        assertMvcResult(mvcResult);

    }

    /**
     * Principal User : "jushford@manu.com"
     *
     * @throws Exception in case of any interruption
     */
    @Test
    @WithUserDetails("jushford@manu.com")
    void getUserByEmiratesIDControllerTest() throws Exception {
        String EMIRATES_ID = "ABCDEFT12345";

        MvcResult mvcResult = mockMvc.perform(get("/user/emiratesID/" + EMIRATES_ID).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(EMIRATES_ID)))
                .andDo(print())
                .andReturn();

        assertMvcResult(mvcResult);
    }

    private void assertMvcResult(MvcResult mvcResult) {
        assertNotNull(mvcResult);
        //this further checks if CSRF token is implemented properly for test cases as well
        assertNotNull(mvcResult.getResponse().getHeader(ApplicationConstants.CSRF_HEADERS.RESPONSE_TOKEN_NAME.getValue()));

    }

}