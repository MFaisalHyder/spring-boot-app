package com.spring.project.utc;

import com.spring.project.config.TestConstants;
import com.spring.project.constant.ApplicationConstants;
import com.spring.project.dto.EmployeeDTO;
import com.spring.project.dto.RoleDTO;
import com.spring.project.entity.Employee;
import com.spring.project.entity.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.modelmapper.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeDTOUnitTest {

    private static ModelMapper modelMapper;
    private static Employee employee;
    private static Role role;
    private static EmployeeDTO employeeDTO;
    private static RoleDTO roleDTO;

    @BeforeAll
    void beforeAll() {
        modelMapper = new ModelMapper();

        Provider<LocalDateTime> localDateProvider = new AbstractProvider<LocalDateTime>() {
            @Override
            public LocalDateTime get() {
                return LocalDateTime.now();
            }
        };

        Converter<String, LocalDateTime> toStringDate = new AbstractConverter<String, LocalDateTime>() {
            @Override
            protected LocalDateTime convert(String source) {
                DateTimeFormatter format = DateTimeFormatter.ofPattern(ApplicationConstants.DATE_TIME_FORMAT);

                return LocalDateTime.parse(source, format);
            }
        };


        modelMapper.createTypeMap(String.class, LocalDateTime.class);
        modelMapper.addConverter(toStringDate);
        modelMapper.getTypeMap(String.class, LocalDateTime.class).setProvider(localDateProvider);

        role = new Role();
        role.setID(TestConstants.ADMIN_ROLE_ID);
        role.setCreatedBy(TestConstants.FIRST_NAME);
        role.setCreatedDate(TestConstants.TIME);
        role.setModifiedBy(TestConstants.FIRST_NAME);
        role.setModifiedDate(TestConstants.TIME);
        role.setName(TestConstants.ROLE_ADMIN);

        employee = new Employee();
        employee.setID(-1000L);
        employee.setCreatedBy(TestConstants.FIRST_NAME);
        employee.setCreatedDate(TestConstants.TIME);
        employee.setModifiedBy(TestConstants.FIRST_NAME);
        employee.setModifiedDate(TestConstants.TIME);
        employee.setEmiratesID(TestConstants.EMIRATES_ID + "001");
        employee.setFirstName(TestConstants.FIRST_NAME);
        employee.setLastName(TestConstants.LAST_NAME);
        employee.setEmail(TestConstants.EMAIL);
        employee.setStaffID(TestConstants.STAFF_ID + "_001");
        employee.setPassword(TestConstants.PASSWORD);
        employee.setRole(role);
    }

    @Test
    void entityToDTOMappingTest() {
        roleDTO = modelMapper.map(role, RoleDTO.class);

        assertEquals(role.getID(), roleDTO.getID());

        employeeDTO = modelMapper.map(employee, EmployeeDTO.class);

        assertEquals(Long.valueOf(-1000L), employeeDTO.getID());
        assertEquals(employee.getEmiratesID(), employeeDTO.getEmiratesID());
        assertEquals(employee.getEmail(), employeeDTO.getEmail());
        assertNotNull(employeeDTO.getCreatedDate());
        assertNotNull(employeeDTO.getModifiedDate());
        assertNotNull(employeeDTO.getRole().getName());

    }

    @Test
    void DtoToEntityMappingTest() {
        roleDTO = new RoleDTO();
        roleDTO.setID(TestConstants.ADMIN_ROLE_ID);
        roleDTO.setCreatedBy(TestConstants.FIRST_NAME);
        roleDTO.setCreatedDate(TestConstants.TIME.format(DateTimeFormatter.ofPattern(ApplicationConstants.DATE_TIME_FORMAT)));
        roleDTO.setModifiedBy(TestConstants.FIRST_NAME);
        roleDTO.setModifiedDate(TestConstants.TIME.format(DateTimeFormatter.ofPattern(ApplicationConstants.DATE_TIME_FORMAT)));
        roleDTO.setName(TestConstants.ROLE_ADMIN);

        employeeDTO = new EmployeeDTO();
        employeeDTO.setID(-5000L);
        employeeDTO.setCreatedBy(TestConstants.FIRST_NAME);
        employeeDTO.setCreatedDate(TestConstants.TIME.format(DateTimeFormatter.ofPattern(ApplicationConstants.DATE_TIME_FORMAT)));
        employeeDTO.setModifiedBy(TestConstants.FIRST_NAME);
        employeeDTO.setModifiedDate(TestConstants.TIME.format(DateTimeFormatter.ofPattern(ApplicationConstants.DATE_TIME_FORMAT)));
        employeeDTO.setEmiratesID(TestConstants.EMIRATES_ID + "001");
        employeeDTO.setFirstName(TestConstants.FIRST_NAME);
        employeeDTO.setLastName(TestConstants.LAST_NAME);
        employeeDTO.setEmail(TestConstants.EMAIL);
        employeeDTO.setStaffID(TestConstants.STAFF_ID + "_001");
        employeeDTO.setPassword(TestConstants.PASSWORD);
        employeeDTO.setRole(roleDTO);

        role = new Role();
        role = modelMapper.map(roleDTO, Role.class);

        assertEquals(roleDTO.getID(), role.getID());

        employee = new Employee();
        employee = modelMapper.map(employeeDTO, Employee.class);

        assertEquals(Long.valueOf(-5000L), employee.getID());
        assertEquals(employeeDTO.getEmiratesID(), employee.getEmiratesID());
        assertEquals(employeeDTO.getEmail(), employee.getEmail());
        assertNotNull(employee.getCreatedDate());
        assertNotNull(employee.getModifiedDate());
        assertNotNull(employee.getRole().getName());

    }

}