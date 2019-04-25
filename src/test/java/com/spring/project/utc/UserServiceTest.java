package com.spring.project.utc;

import com.spring.project.config.BaseTest;
import com.spring.project.constant.ApplicationConstants;
import com.spring.project.dto.EmployeeDTO;
import com.spring.project.manager.UserManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserServiceTest")
class UserServiceTest extends BaseTest {

    private static final String EMIRATES_ID = "ABCDEFT12345";
    private static final String FIRST_NAME = "Muhammad Faisal";
    private static final String LAST_NAME = "Hyder";
    private static final String STAFF_ID = "SE_007";
    private static final String PASSWORD = "12345ABCDEF";

    private static EmployeeDTO employee;

    @Autowired
    private UserManager userManager;

    /**
     * We need to declare such methods as static if we are not using @TestInstance for Test class
     * But static should be avoided as it will not be cleaned up when all test cases are executed
     */
    @BeforeAll
    void beforeAll() {
        employee = new EmployeeDTO();

        employee.setEmiratesID(EMIRATES_ID + "001");
        employee.setFirstName(FIRST_NAME);
        employee.setLastName(LAST_NAME);
        employee.setStaffID(STAFF_ID + "_001");
        employee.setPassword(PASSWORD);
    }

    @Test
    void findUserByFirstNameTest() throws Exception {
        List<EmployeeDTO> employees = userManager.findUserByFirstName(FIRST_NAME);

        assertNotNull(employees);
        assertTrue(employees.size() > 1);

    }

    @Test
    void findByEmiratesIDNumberTest() throws Exception {
        EmployeeDTO employee = userManager.findByEmiratesIDNumber(EMIRATES_ID);

        assertNotNull(employee);
        assertNotNull(employee.getCreatedDate());
        assertNotNull(employee.getCreatedBy());
        assertEquals(EMIRATES_ID, employee.getEmiratesID());

    }

    @Test
    @SuppressWarnings({"unchecked"})
    void findAllUsersTest() throws Exception {
        Map<String, Object> result = userManager.findAllUsers();

        assertNotNull(result);
        assertNotNull(result.get("status"));
        assertEquals(result.get("status"), ApplicationConstants.GeneralConstants.SUCCESS.getValue());
        assertNotNull(result.get("usersList"));

        List<EmployeeDTO> employees = (List) result.get("usersList");
        assertNotNull(employees);

    }

    @Test
    void registerUserTest() throws Exception {
        EmployeeDTO registeredEmployee = userManager.registerUser(employee);

        assertAll("Registered employee should not be null",
                () -> assertNotNull(registeredEmployee),
                () -> assertNotNull(registeredEmployee.getCreatedBy()),
                () -> assertNotNull(registeredEmployee.getCreatedDate())
        );

    }

}