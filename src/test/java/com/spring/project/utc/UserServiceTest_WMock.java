package com.spring.project.utc;

import com.spring.project.constant.ApplicationConstants;
import com.spring.project.dto.RoleDTO;
import com.spring.project.dto.UserDTO;
import com.spring.project.service.UserService;
import com.spring.project.utility.PasswordUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

/**
 * Unit test cases are carried out using Mockito framework. Since it is Unit testing, we are more focused on our
 * code/logic written in code rather than data it uses or manipulates.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserServiceTest_WithMock")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest_WMock {

    /*
    @Mock creates Mock of the annotated class
     */
    @Mock
    private ModelMapper modelMapper;

    /*
    @InjectMocks also creates Mock implementation of the annotated class and injects other @Mocks classes into it
     */
    @Mock
    private UserService userService;

    @Configuration
    class Config {

        @Bean
        PasswordUtil passwordUtil() {

            return new PasswordUtil(passwordEncoder());
        }

        @Bean
        BCryptPasswordEncoder passwordEncoder() {

            return new BCryptPasswordEncoder();
        }

        @Bean
        public ModelMapper modelMapper() {
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

            return modelMapper;
        }
    }

    @SuppressWarnings({"unchecked"})
    @Test
    @DisplayName("Test getAllUsers()")
    void testGetAllUsers() {
        String EmiratesID = "ABCDEF12345";
        String FirstName = "Muhammad Faisal";

        LinkedHashMap<String, Object> usersList = new LinkedHashMap<>();
        List<UserDTO> userList = new ArrayList<>();

        RoleDTO userRole = new RoleDTO();
        userRole.setID(1L);
        userRole.setCreatedDate(LocalDateTime.now().toString());
        userRole.setCreatedBy("Muhammad Faisal Hyder");
        userRole.setName("USER");

        UserDTO user = new UserDTO();
        user.setID(1L);
        user.setCreatedBy("Muhammad Faisal Hyder");
        user.setCreatedDate(LocalDateTime.now().toString());
        user.setEmiratesID(EmiratesID);
        user.setFirstName(FirstName);
        user.setLastName("Hyder");
        user.setPassword(new Config().passwordUtil().encryptPassword("abcdef12345"));
        user.setRole(userRole);

        userList.add(user);
        usersList.put("status", ApplicationConstants.GeneralConstants.SUCCESS);
        usersList.put("usersList", userList);

        doReturn(usersList).when(userService).findAllUsers();

        List<UserDTO> allUsers = (List<UserDTO>) userService.findAllUsers().get("usersList");

        assertAll("It should return what we have added above",
                () -> assertNotNull(user),
                () -> assertEquals(1, allUsers.size()),
                () -> assertEquals(EmiratesID, allUsers.get(0).getEmiratesID()),
                () -> assertEquals(FirstName, allUsers.get(0).getFirstName())
        );

    }

}