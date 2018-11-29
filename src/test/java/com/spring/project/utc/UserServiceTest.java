package com.spring.project.utc;

import com.spring.project.entity.Employee;
import com.spring.project.repository.UserRepository;
import com.spring.project.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Unit test cases are carried out using Mockito framework. Since it is Unit testing, we are more focused on our
 * code/logic written in code rather than data it uses or manipulates.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserServiceTest")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Test getAllUsers()")
    void testGetAllUsers() {
        List<Employee> employeeList = new ArrayList<>();

        Employee employee = new Employee();
        employee.setID(1L);
        employee.setCreatedDate(LocalDateTime.now());
        employee.setEmiratesID("ABCDEF12345");
        employee.setFirstName("Muhammad Faisal");
        employee.setLastName("Hyder");

        employeeList.add(employee);

        when(userRepository.findAll()).thenReturn(employeeList);

        List<Employee> employees = (List<Employee>) userService.findAllUsers().get("usersList");

        assertAll("It should return what we have added above",
                () -> assertNotNull(employee),
                () -> assertEquals(1, employees.size()),
                () -> assertEquals("ABCDEF12345", employees.get(0).getEmiratesID()),
                () -> assertEquals("Muhammad Faisal", employees.get(0).getFirstName())
        );

    }

}