package com.spring.project.utc;

import com.spring.project.config.BaseTest;
import com.spring.project.constant.ApplicationConstants;
import com.spring.project.dto.UserDTO;
import com.spring.project.dto.RoleDTO;
import com.spring.project.manager.UserManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserServiceTest")
class UserServiceTest extends BaseTest {

    private static final Long ADMIN_ROLE_ID = -200L;
    private static final String EMIRATES_ID = "ABCDEFT12345";
    private static final String FIRST_NAME = "Muhammad Faisal";
    private static final String LAST_NAME = "Hyder";
    private static final String STAFF_ID = "SE_007";
    private static final String PASSWORD = "12345ABCDEF";
    private static final String EMAIL = "abc@def.com";

    private static UserDTO user;
    private static RoleDTO role;
    private UserManager userManager;

    @Autowired
    public UserServiceTest(final UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * We need to declare such methods as static if we are not using @TestInstance for Test class
     * But static should be avoided as it will not be cleaned up when all test cases are executed
     */
    @BeforeAll
    void beforeAll() {
        role = new RoleDTO();
        role.setID(ADMIN_ROLE_ID);

        user = new UserDTO();
        user.setEmiratesID(EMIRATES_ID + "001");
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setEmail(EMAIL);
        user.setStaffID(STAFF_ID + "_001");
        user.setPassword(PASSWORD);
        user.setRole(role);

    }

    @Test
    void greetUserTest() {
        String greetingMessage = userManager.greetUser(FIRST_NAME);

        assertNotNull(greetingMessage);
        assertEquals(ApplicationConstants.GeneralConstants.WELCOME.getValue() + FIRST_NAME, greetingMessage);

    }

    @Test
    void findUserByFirstNameTest() {
        List<UserDTO> employees = userManager.findUserByFirstName(FIRST_NAME);

        assertNotNull(employees);
        assertTrue(employees.size() > 1);
        for (UserDTO tempEmployee : employees) {
            assertNotNull(tempEmployee.getID());
            assertNotNull(tempEmployee.getCreatedDate());
            assertNotNull(tempEmployee.getCreatedBy());
            assertNotNull(tempEmployee.getRole());
            assertNotNull(tempEmployee.getRole().getID());
            assertNotNull(tempEmployee.getRole().getName());
        }

    }

    @Test
    void findByEmiratesIDTest() throws Exception {
        UserDTO employee = userManager.findUserByEmiratesID(EMIRATES_ID);

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

        List<UserDTO> employees = (List) result.get("usersList");
        assertNotNull(employees);

    }

    @Test
    @WithUserDetails("faisal.hyder@gmail.com")
        //required as Spring will look for a principal object for JPA Auditing
    void registerUserTest() throws Exception {
        UserDTO registeredUser = userManager.registerUser(user);

        assertAll("Registered user should not be null",
                () -> assertNotNull(registeredUser),
                () -> assertNotNull(registeredUser.getID()),
                () -> assertNotNull(registeredUser.getCreatedBy()),
                () -> assertNotNull(registeredUser.getCreatedDate()),
                () -> assertNotNull(registeredUser.getRole()),
                () -> assertNotNull(registeredUser.getRole().getID()),
                () -> assertNotNull(registeredUser.getRole().getCreatedBy()),
                () -> assertNotNull(registeredUser.getRole().getCreatedDate()),
                () -> assertEquals(ADMIN_ROLE_ID, registeredUser.getRole().getID())

        );

    }

    @Test
    void findUserByLastNameTest() throws Exception {
        List<UserDTO> employees = userManager.findUserByLastName(LAST_NAME);

        assertNotNull(employees);

    }

    @Test
    void findUserByStaffIDTest() {
        String STAFF_ID = "S779381";

        UserDTO user = userManager.findUserByStaffID(STAFF_ID);

        assertNotNull(user);
        assertEquals(STAFF_ID, user.getStaffID());

    }

    @Test
    void findUserByEmailTest() {
        String EMAIL = "john.Smith@gmail.com";
        String EMIRATES_ID = "BCDEFGT12345";

        UserDTO user = userManager.findUserByEmail(EMAIL);

        assertNotNull(user);
        assertEquals(EMAIL, user.getEmail());
        assertEquals(EMIRATES_ID, user.getEmiratesID());

    }

}