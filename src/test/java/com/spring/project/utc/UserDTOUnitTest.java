package com.spring.project.utc;

import com.spring.project.config.TestConstants;
import com.spring.project.constant.ApplicationConstants;
import com.spring.project.dto.UserDTO;
import com.spring.project.dto.RoleDTO;
import com.spring.project.entity.User;
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
class UserDTOUnitTest {

    private static ModelMapper modelMapper;
    private static User user;
    private static Role role;
    private static UserDTO userDTO;
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

        user = new User();
        user.setID(-1000L);
        user.setCreatedBy(TestConstants.FIRST_NAME);
        user.setCreatedDate(TestConstants.TIME);
        user.setModifiedBy(TestConstants.FIRST_NAME);
        user.setModifiedDate(TestConstants.TIME);
        user.setEmiratesID(TestConstants.EMIRATES_ID + "001");
        user.setFirstName(TestConstants.FIRST_NAME);
        user.setLastName(TestConstants.LAST_NAME);
        user.setEmail(TestConstants.EMAIL);
        user.setStaffID(TestConstants.STAFF_ID + "_001");
        user.setPassword(TestConstants.PASSWORD);
        user.setRole(role);
    }

    @Test
    void entityToDTOMappingTest() {
        roleDTO = modelMapper.map(role, RoleDTO.class);

        assertEquals(role.getID(), roleDTO.getID());

        userDTO = modelMapper.map(user, UserDTO.class);

        assertEquals(Long.valueOf(-1000L), userDTO.getID());
        assertEquals(user.getEmiratesID(), userDTO.getEmiratesID());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertNotNull(userDTO.getCreatedDate());
        assertNotNull(userDTO.getModifiedDate());
        assertNotNull(userDTO.getRole().getName());

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

        userDTO = new UserDTO();
        userDTO.setID(-5000L);
        userDTO.setCreatedBy(TestConstants.FIRST_NAME);
        userDTO.setCreatedDate(TestConstants.TIME.format(DateTimeFormatter.ofPattern(ApplicationConstants.DATE_TIME_FORMAT)));
        userDTO.setModifiedBy(TestConstants.FIRST_NAME);
        userDTO.setModifiedDate(TestConstants.TIME.format(DateTimeFormatter.ofPattern(ApplicationConstants.DATE_TIME_FORMAT)));
        userDTO.setEmiratesID(TestConstants.EMIRATES_ID + "001");
        userDTO.setFirstName(TestConstants.FIRST_NAME);
        userDTO.setLastName(TestConstants.LAST_NAME);
        userDTO.setEmail(TestConstants.EMAIL);
        userDTO.setStaffID(TestConstants.STAFF_ID + "_001");
        userDTO.setPassword(TestConstants.PASSWORD);
        userDTO.setRole(roleDTO);

        role = new Role();
        role = modelMapper.map(roleDTO, Role.class);

        assertEquals(roleDTO.getID(), role.getID());

        user = new User();
        user = modelMapper.map(userDTO, User.class);

        assertEquals(Long.valueOf(-5000L), user.getID());
        assertEquals(userDTO.getEmiratesID(), user.getEmiratesID());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertNotNull(user.getCreatedDate());
        assertNotNull(user.getModifiedDate());
        assertNotNull(user.getRole().getName());

    }

}