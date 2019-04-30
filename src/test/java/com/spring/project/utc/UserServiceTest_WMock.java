package com.spring.project.utc;

import com.spring.project.dto.EmployeeDTO;
import com.spring.project.entity.Employee;
import com.spring.project.entity.Role;
import com.spring.project.repository.UserRepository;
import com.spring.project.service.UserService;
import com.spring.project.utility.PasswordUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Unit test cases are carried out using Mockito framework. Since it is Unit testing, we are more focused on our
 * code/logic written in code rather than data it uses or manipulates.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserServiceTest_WithMock")
class UserServiceTest_WMock {

    /*
    @Mock creates Mock of the annotated class
     */
    @Mock
    private UserRepository userRepository;

    /*
    @InjectMocks also creates Mock implementation of the annotated class and injects other @Mocks classes into it
     */
    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordUtil passwordUtil;

    @Test
    @DisplayName("Test getAllUsers()")
    void testGetAllUsers() throws Exception {
        List<Employee> employeeList = new ArrayList<>();

        Role userRole = new Role();
        userRole.setID(1L);
        userRole.setCreatedDate(LocalDateTime.now());
        userRole.setCreatedBy("Muhammad Faisal Hyder");
        userRole.setName("USER");

        Employee employee = new Employee();
        employee.setID(1L);
        employee.setCreatedBy("Muhammad Faisal Hyder");
        employee.setCreatedDate(LocalDateTime.now());
        employee.setEmiratesID("ABCDEF12345");
        employee.setFirstName("Muhammad Faisal");
        employee.setLastName("Hyder");
        employee.setPassword(passwordUtil.encryptPassword("abcdef12345"));
        employee.setRole(userRole);

        employeeList.add(employee);

        when(userRepository.findAll()).thenReturn(employeeList);

        @SuppressWarnings({"unchecked"})
        List<EmployeeDTO> employees = (List<EmployeeDTO>) userService.findAllUsers().get("usersList");

        assertAll("It should return what we have added above",
                () -> assertNotNull(employee),
                () -> assertEquals(1, employees.size()),
                () -> assertEquals("ABCDEF12345", employees.get(0).getEmiratesID()),
                () -> assertEquals("Muhammad Faisal", employees.get(0).getFirstName())
        );

    }

}