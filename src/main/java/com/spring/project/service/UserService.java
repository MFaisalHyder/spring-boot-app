package com.spring.project.service;

import com.spring.project.config.PropertiesConfig;
import com.spring.project.constant.ApplicationConstants;
import com.spring.project.constant.ApplicationProperties;
import com.spring.project.dto.LoginDTO;
import com.spring.project.dto.RoleDTO;
import com.spring.project.dto.UserDTO;
import com.spring.project.entity.Role;
import com.spring.project.entity.User;
import com.spring.project.exception.InvalidInputException;
import com.spring.project.exception.UserNotFoundException;
import com.spring.project.exception.UserNotRegisteredException;
import com.spring.project.manager.UserManager;
import com.spring.project.repository.RoleRepository;
import com.spring.project.repository.UserRepository;
import com.spring.project.utility.PasswordUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class UserService implements UserManager {

    private static final Logger userServiceLogger = LogManager.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PropertiesConfig propertiesConfig;
    private final PasswordUtil passwordUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(final UserRepository userRepository, final PropertiesConfig propertiesConfig,
                       final PasswordUtil passwordUtil, final RoleRepository roleRepository, final ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.propertiesConfig = propertiesConfig;
        this.passwordUtil = passwordUtil;
        this.modelMapper = modelMapper;

    }

    @Override
    public String greetUser(String name) {
        userServiceLogger.info("UserService.greetUser() :: method call ---- STARTS");

        if (StringUtils.isEmpty(name)) {
            userServiceLogger.error("UserService.greetUser() :: User name is unavailable = '{}'", name);

            throw new InvalidInputException("User name is missing = " + name);
        }

        userServiceLogger.info("UserService.greetUser() :: method call ---- ENDS");

        return ApplicationConstants.GeneralConstants.WELCOME.getValue() + name;
    }

    @Override
    public List<UserDTO> findUserByFirstName(String firstName) throws UserNotFoundException {
        userServiceLogger.info("UserService.findUserByFirstName() :: method call ---- STARTS");

        List<User> usersFoundFromDB;
        UserDTO user;
        List<UserDTO> users;

        try {
            usersFoundFromDB = userRepository.findByFirstName(firstName);

            if (usersFoundFromDB == null) {
                userServiceLogger.debug("No users found for given First Name = " + firstName);

                return null;
            }

            users = new ArrayList<>();

            for (User tempUser : usersFoundFromDB) {
                user = modelMapper.map(tempUser, UserDTO.class);

                users.add(user);
            }

            userServiceLogger.info("UserService.findUserByFirstName() :: method call ---- ENDS");

            return users;

        } catch (Exception exception) {
            userServiceLogger.error("UserService.findUserByFirstName() ::  Some error occurred while finding user ---- FAILED");

            throw new UserNotFoundException(propertiesConfig.getProperty(ApplicationProperties.Messages.UNABLE_TO_FIND_USER.getValue()), exception.getMessage());
        }
    }

    @Override
    public UserDTO findUserByEmiratesID(String emiratesID) throws UserNotFoundException {
        userServiceLogger.info("UserService.findByEmiratesID() :: method call ---- STARTS");

        User userFoundFromDB;
        UserDTO user;

        try {
            userFoundFromDB = userRepository.findByEmiratesID(emiratesID);

            if (userFoundFromDB == null) {
                userServiceLogger.debug("User not found for given EmiratesID = " + emiratesID);

                return null;
            }

            /*
            user = copyUserPropertiesFromEntityToDTO(userFoundFromDB);
            */
            user = modelMapper.map(userFoundFromDB, UserDTO.class);

            userServiceLogger.info("UserService.findByEmiratesID() :: method call ---- ENDS");

            return user;

        } catch (Exception exception) {
            userServiceLogger.error("UserService.findUserByEmiratesID() ::  Some error occurred while finding user ---- FAILED");

            throw new UserNotFoundException(propertiesConfig.getProperty(ApplicationProperties.Messages.UNABLE_TO_FIND_USER.getValue()), exception.getMessage());

        }
    }

    /**
     * Finds all users
     *
     * @return LinkedHashMap<String               ,                               Object> -> "usersList", List<EmployeeDTO>
     * @throws UserNotFoundException is thrown if any interruption is occurred
     */
    @Override
    public LinkedHashMap<String, Object> findAllUsers() throws UserNotFoundException {
        userServiceLogger.info("UserService.findAllUsers() :: method call ---- STARTS");

        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        List<User> allUsersInDB;
        UserDTO user;
        List<UserDTO> allUsers;
        String status;

        try {
            allUsersInDB = userRepository.findAll();

            status = allUsersInDB != null && allUsersInDB.size() > 0
                    ? ApplicationConstants.GeneralConstants.SUCCESS.getValue()
                    : ApplicationConstants.GeneralConstants.ZERO_RECORDS.getValue();


            if (allUsersInDB == null || allUsersInDB.size() == 0) {
                userServiceLogger.debug("No User found");

                return null;

            }

            allUsers = new ArrayList<>();

            for (User tempUser : allUsersInDB) {
                //user = copyUserPropertiesFromEntityToDTO(tempUser);
                user = modelMapper.map(tempUser, UserDTO.class);

                allUsers.add(user);

            }

            result.put("status", status);
            result.put("usersList", allUsers);

            userServiceLogger.info("UserService.findAllUsers() :: method call ---- ENDS");

            return result;

        } catch (Exception exception) {
            userServiceLogger.error("UserService.findAllUsers() :: Some error occurred while finding users list ---- FAILED");

            throw new UserNotFoundException(propertiesConfig.getProperty(ApplicationProperties.Messages.UNABLE_TO_FIND_USER_LIST.getValue()), exception.getMessage());

        }
    }

    /**
     * Service method to add/register an user in DB
     *
     * @param user UserDTO Object
     * @return UserDTO
     * @throws UserNotRegisteredException is thrown if any interruption is occurred
     */
    @Override
    public UserDTO registerUser(UserDTO user) throws UserNotRegisteredException {
        userServiceLogger.info("UserService.registerUser() :: method call ---- STARTS");

        User userToBeRegistered;
        Role role;

        try {
            /*
            BeanUtils.copyProperties(user, userToBeRegistered, "role");
            */
            userToBeRegistered = modelMapper.map(user, User.class);

            role = roleRepository.findById(user.getRole().getID()).orElse(null);

            if (role == null) {
                throw new UserNotRegisteredException(propertiesConfig.getProperty(ApplicationProperties.Messages.UNABLE_TO_REGISTER_USER.getValue()),
                        propertiesConfig.getProperty(ApplicationProperties.Messages.NO_ROLE_FOUND.getValue()));

            }

            userToBeRegistered.setPassword(passwordUtil.encryptPassword(user.getPassword()));
            userToBeRegistered.setRole(role);

            userToBeRegistered = userRepository.save(userToBeRegistered);

            /*
            BeanUtils.copyProperties(userToBeRegistered, user, "role");
            employee.setRole(copyRolePropertiesFromEntityToDTO(role));
            employee.setCreatedDate(userToBeRegistered.getCreatedDate() != null ? userToBeRegistered.getCreatedDate().toString() : null);
            employee.setModifiedDate(userToBeRegistered.getModifiedDate() != null ? userToBeRegistered.getModifiedDate().toString() : null);
            */

            user = modelMapper.map(userToBeRegistered, UserDTO.class);

            userServiceLogger.info("UserService.registerUser() :: method call ---- ENDS");

            return user;

        } catch (Exception exception) {
            userServiceLogger.error("UserService.registerUser() :: method call ---- FAILED");

            throw new UserNotRegisteredException(propertiesConfig.getProperty(ApplicationProperties.Messages.UNABLE_TO_REGISTER_USER.getValue()), exception.getMessage());
        }

    }

    /**
     * Service method to login user
     *
     * @param user UserDTO Object
     * @return UserDTO
     * @throws UserNotFoundException is thrown if any interruption is occurred
     */
    @Override
    public LoginDTO loginUser(UserDTO user) throws UserNotFoundException {
        userServiceLogger.info("UserService.loginUser() :: method call ---- STARTS");

        LoginDTO loginResponse = new LoginDTO();

        try {
            userRepository.findByEmail(user.getEmail());

            /*
            Authentication request = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            Authentication result = authenticationManager.authenticate(request);

            SecurityContextHolder.getContext().setAuthentication(result);
            */

        } catch (Exception exception) {

        }

        userServiceLogger.info("UserService.loginUser() :: method call ---- ENDS");

        return loginResponse;
    }

    /**
     * Finds users with given last name
     *
     * @param lastName "last name of user"
     * @return List<UserDTO>
     * @throws UserNotFoundException is thrown if any interruption is occurred
     */
    @Override
    public List<UserDTO> findUserByLastName(String lastName) throws UserNotFoundException {
        userServiceLogger.info("UserService.findUserByLastName() :: method call ---- STARTS");

        List<User> usersFoundFromDB;
        List<UserDTO> users;

        try {
            usersFoundFromDB = userRepository.findByLastName(lastName);

            if (usersFoundFromDB == null || usersFoundFromDB.size() == 0) {
                userServiceLogger.debug("UserService.findUserByLastName() :: No user found with given last name = {}", lastName);

                return null;
            }

            users = new ArrayList<>();

            for (User tempUser : usersFoundFromDB) {
                UserDTO user = modelMapper.map(tempUser, UserDTO.class);

                users.add(user);
            }

        } catch (Exception exception) {
            userServiceLogger.error("UserService.findUserByLastName() :: method call ---- FAILED");

            throw new UserNotFoundException(propertiesConfig.getProperty(ApplicationProperties.Messages.UNABLE_TO_FIND_USER.getValue()), exception.getMessage());

        }

        userServiceLogger.info("UserService.findUserByLastName() :: method call ---- ENDS");

        return users;
    }

    /**
     * Finds user given their StaffID
     *
     * @param staffID "EMP_102"
     * @return UserDTO
     * @throws UserNotFoundException is thrown if any interruption is occurred
     */
    @Override
    public UserDTO findUserByStaffID(String staffID) throws UserNotFoundException {
        userServiceLogger.info("UserService.findUserByStaffID() :: method call ---- STARTS");

        User userFoundFromDB;
        UserDTO user;

        try {
            userFoundFromDB = userRepository.findByStaffID(staffID);

            if (userFoundFromDB == null) {
                userServiceLogger.debug("UserService.findUserByStaffID() :: No user found with given staff ID = {}", staffID);

                return null;
            }

            user = modelMapper.map(userFoundFromDB, UserDTO.class);

        } catch (Exception exception) {
            userServiceLogger.info("UserService.findUserByStaffID() :: method call ---- FAILED");

            throw new UserNotFoundException(propertiesConfig.getProperty(ApplicationProperties.Messages.UNABLE_TO_FIND_USER.getValue()), exception.getMessage());
        }

        userServiceLogger.info("UserService.findUserByStaffID() :: method call ---- ENDS");

        return user;
    }

    /**
     * Finds user given their email address
     *
     * @param email "abc@gmail.com"
     * @return UserDTO
     * @throws UserNotFoundException is thrown if any interruption is occurred
     */
    @Override
    public UserDTO findUserByEmail(String email) throws UserNotFoundException {
        userServiceLogger.info("UserService.findUserByEmail() :: method call ---- STARTS");

        User userFoundFromDB;
        UserDTO user;

        try {
            userFoundFromDB = userRepository.findByEmail(email);

            if (userFoundFromDB == null) {
                userServiceLogger.debug("UserService.findUserByEmail() :: No user found with given email = {}", email);

                return null;
            }

            user = modelMapper.map(userFoundFromDB, UserDTO.class);

        } catch (Exception exception) {
            userServiceLogger.info("UserService.findUserByEmail() :: method call ---- FAILED");

            throw new UserNotFoundException(propertiesConfig.getProperty(ApplicationProperties.Messages.UNABLE_TO_FIND_USER.getValue()), exception.getMessage());
        }

        userServiceLogger.info("UserService.findUserByEmail() :: method call ---- ENDS");

        return user;
    }

    private UserDTO copyUserPropertiesFromEntityToDTO(User userEntity) {
        UserDTO userDTO = new UserDTO();

        if (userEntity != null) {
            BeanUtils.copyProperties(userEntity, userDTO);
            userDTO.setRole(copyRolePropertiesFromEntityToDTO(userEntity.getRole()));
            userDTO.setCreatedDate(userEntity.getCreatedDate() != null ? userEntity.getCreatedDate().toString() : null);
            userDTO.setModifiedDate(userEntity.getModifiedDate() != null ? userEntity.getModifiedDate().toString() : null);
        }

        return userDTO;

    }

    private RoleDTO copyRolePropertiesFromEntityToDTO(Role roleEntity) {
        RoleDTO roleDTO = new RoleDTO();

        if (roleEntity != null) {
            roleDTO.setID(roleEntity.getID());
            roleDTO.setCreatedDate(!StringUtils.isEmpty(roleEntity.getCreatedDate()) ? roleEntity.getCreatedDate().toString() : null);
            roleDTO.setCreatedBy(!StringUtils.isEmpty(roleEntity.getCreatedBy()) ? roleEntity.getCreatedBy() : null);
            roleDTO.setModifiedDate(!StringUtils.isEmpty(roleEntity.getModifiedDate()) ? roleEntity.getModifiedDate().toString() : null);
            roleDTO.setModifiedBy(!StringUtils.isEmpty(roleEntity.getModifiedBy()) ? roleEntity.getModifiedBy() : null);
            roleDTO.setName(roleEntity.getName());

        }

        return roleDTO;

    }

}