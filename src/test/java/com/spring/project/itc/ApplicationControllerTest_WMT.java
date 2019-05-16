package com.spring.project.itc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.project.config.BaseTest_WMT;
import com.spring.project.controller.ApplicationController;
import com.spring.project.dto.UserDTO;
import com.spring.project.dto.RoleDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ApplicationController.class)
@DisplayName("ApplicationControllerTest_WMT - WebMVCTest")
class ApplicationControllerTest_WMT extends BaseTest_WMT {

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;

    @Autowired
    public ApplicationControllerTest_WMT(final ObjectMapper objectMapper, final MockMvc mockMvc) {
        this.objectMapper = objectMapper;
        this.mockMvc = mockMvc;

    }

    private static UserDTO user;

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

        user = new UserDTO();
        user.setEmiratesID(EMIRATES_ID);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setEmail(EMAIL);
        user.setStaffID(STAFF_ID);
        user.setPassword(PASSWORD);
        user.setRole(role);

    }

    @Test
    void registerUserControllerTest() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}