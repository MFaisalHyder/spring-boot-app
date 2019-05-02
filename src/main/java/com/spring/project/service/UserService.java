package com.spring.project.service;

import com.spring.project.Application;
import com.spring.project.config.PropertiesConfig;
import com.spring.project.constant.ApplicationConstants;
import com.spring.project.constant.ApplicationProperties;
import com.spring.project.dto.EmployeeDTO;
import com.spring.project.dto.RoleDTO;
import com.spring.project.entity.Employee;
import com.spring.project.entity.Role;
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
    public List<EmployeeDTO> findUserByFirstName(String firstName) throws UserNotFoundException {
        userServiceLogger.info("UserService.findUserByFirstName() :: method call ---- STARTS");

        List<Employee> usersFoundFromDB;
        EmployeeDTO employee;
        List<EmployeeDTO> employees;

        try {
            usersFoundFromDB = userRepository.findByFirstName(firstName);

            if (usersFoundFromDB == null) {
                userServiceLogger.debug("No users found for given First Name = " + firstName);

                return null;
            }

            employees = new ArrayList<>();

            for (Employee tempEmployee : usersFoundFromDB) {
                employee = modelMapper.map(tempEmployee, EmployeeDTO.class);

                employees.add(employee);
            }

            userServiceLogger.info("UserService.findUserByFirstName() :: method call ---- ENDS");

            return employees;

        } catch (Exception exception) {
            userServiceLogger.error("UserService.findUserByFirstName() ::  Some error occurred while finding user ---- FAILED");

            throw new UserNotFoundException(propertiesConfig.getProperty(ApplicationProperties.Messages.UNABLE_TO_FIND_USER.getValue()), exception.getMessage());
        }
    }

    @Override
    public EmployeeDTO findUserByEmiratesID(String emiratesID) throws UserNotFoundException {
        userServiceLogger.info("UserService.findByEmiratesID() :: method call ---- STARTS");

        Employee userFoundFromDB;
        EmployeeDTO employee;

        try {
            userFoundFromDB = userRepository.findByEmiratesID(emiratesID);

            if (userFoundFromDB == null) {
                userServiceLogger.debug("User not found for given EmiratesID = " + emiratesID);

                return null;
            }

            /*
            employee = copyEmployeePropertiesFromEntityToDTO(userFoundFromDB);
            */
            employee = modelMapper.map(userFoundFromDB, EmployeeDTO.class);

            userServiceLogger.info("UserService.findByEmiratesID() :: method call ---- ENDS");

            return employee;

        } catch (Exception exception) {
            userServiceLogger.error("UserService.findUserByEmiratesID() ::  Some error occurred while finding user ---- FAILED");

            throw new UserNotFoundException(propertiesConfig.getProperty(ApplicationProperties.Messages.UNABLE_TO_FIND_USER.getValue()), exception.getMessage());

        }
    }

    /**
     * Finds all users
     *
     * @return LinkedHashMap<String   ,       Object> -> "usersList", List<EmployeeDTO>
     * @throws UserNotFoundException is thrown if any interruption is occurred
     */
    @Override
    public LinkedHashMap<String, Object> findAllUsers() throws UserNotFoundException {
        userServiceLogger.info("UserService.findAllUsers() :: method call ---- STARTS");

        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        List<Employee> allUsersInDB;
        EmployeeDTO employee;
        List<EmployeeDTO> allEmployees;
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

            allEmployees = new ArrayList<>();

            for (Employee tempEmployee : allUsersInDB) {
                employee = copyEmployeePropertiesFromEntityToDTO(tempEmployee);

                allEmployees.add(employee);

            }

            result.put("status", status);
            result.put("usersList", allEmployees);

            userServiceLogger.info("UserService.findAllUsers() :: method call ---- ENDS");

            return result;

        } catch (Exception exception) {
            userServiceLogger.error("UserService.findAllUsers() :: Some error occurred while finding users list ---- FAILED");

            throw new UserNotFoundException(propertiesConfig.getProperty(ApplicationProperties.Messages.UNABLE_TO_FIND_USER_LIST.getValue()), exception.getMessage());

        }
    }

    /**
     * Service method to add/register an employee in DB
     *
     * @param employee Employee Object
     * @return EmployeeDTO
     * @throws UserNotRegisteredException is thrown if any interruption is occurred
     */
    @Override
    public EmployeeDTO registerUser(EmployeeDTO employee) throws UserNotRegisteredException {
        userServiceLogger.info("UserService.registerUser() :: method call ---- STARTS");

        Employee employeeToBeRegistered = new Employee();
        Role role;

        try {
            /*
            BeanUtils.copyProperties(employee, employeeToBeRegistered, "role");
            */
            employeeToBeRegistered = modelMapper.map(employee, Employee.class);

            role = roleRepository.findById(employee.getRole().getID()).orElse(null);

            if (role == null) {
                throw new UserNotRegisteredException(propertiesConfig.getProperty(ApplicationProperties.Messages.UNABLE_TO_REGISTER_USER.getValue()),
                        propertiesConfig.getProperty(ApplicationProperties.Messages.NO_ROLE_FOUND.getValue()));

            }

            employeeToBeRegistered.setPassword(passwordUtil.encryptPassword(employee.getPassword()));
            employeeToBeRegistered.setRole(role);

            employeeToBeRegistered = userRepository.save(employeeToBeRegistered);

            /*
            BeanUtils.copyProperties(employeeToBeRegistered, employee, "role");
            employee.setRole(copyRolePropertiesFromEntityToDTO(role));
            employee.setCreatedDate(employeeToBeRegistered.getCreatedDate() != null ? employeeToBeRegistered.getCreatedDate().toString() : null);
            employee.setModifiedDate(employeeToBeRegistered.getModifiedDate() != null ? employeeToBeRegistered.getModifiedDate().toString() : null);
            */

            employee = modelMapper.map(employeeToBeRegistered, EmployeeDTO.class);

            userServiceLogger.info("UserService.registerUser() :: method call ---- ENDS");

            return employee;

        } catch (Exception exception) {
            userServiceLogger.error("UserService.registerUser() :: method call ---- FAILED");

            throw new UserNotRegisteredException(propertiesConfig.getProperty(ApplicationProperties.Messages.UNABLE_TO_REGISTER_USER.getValue()), exception.getMessage());
        }

    }

    /**
     * Service method to login user
     *
     * @param employee Employee Object
     * @return EmployeeDTO
     * @throws UserNotFoundException is thrown if any interruption is occurred
     */
    @Override
    public EmployeeDTO loginUser(Employee employee) throws UserNotFoundException {
        userServiceLogger.info("UserService.loginUser() :: method call ---- STARTS");

        EmployeeDTO employeeDTO = new EmployeeDTO();

        userServiceLogger.info("UserService.loginUser() :: method call ---- ENDS");

        return employeeDTO;
    }

    /**
     * Finds users with given last name
     *
     * @param lastName "last name of user"
     * @return List<EmployeeDTO>
     * @throws UserNotFoundException is thrown if any interruption is occurred
     */
    @Override
    public List<EmployeeDTO> findUserByLastName(String lastName) throws UserNotFoundException {
        userServiceLogger.info("UserService.findUserByLastName() :: method call ---- STARTS");

        List<EmployeeDTO> usersWithGivenLastName;
        List<Employee> usersFoundFromDB;

        try {
            usersFoundFromDB = userRepository.findByLastName(lastName);

            if (usersFoundFromDB == null || usersFoundFromDB.size() == 0) {
                userServiceLogger.debug("UserService.findUserByLastName() :: No user found with given last name = {}", lastName);

                return null;
            }

            usersWithGivenLastName = new ArrayList<>();

            for (Employee tempUser : usersFoundFromDB) {
                EmployeeDTO user = modelMapper.map(tempUser, EmployeeDTO.class);

                usersWithGivenLastName.add(user);
            }

        } catch (Exception exception) {
            userServiceLogger.error("UserService.findUserByLastName() :: method call ---- FAILED");

            throw new UserNotFoundException(propertiesConfig.getProperty(ApplicationProperties.Messages.UNABLE_TO_FIND_USER.getValue()), exception.getMessage());

        }

        userServiceLogger.info("UserService.findUserByLastName() :: method call ---- ENDS");

        return usersWithGivenLastName;
    }

    /**
     * Finds user given their StaffID
     *
     * @param staffID "EMP_102"
     * @return EmployeeDTO
     * @throws UserNotFoundException is thrown if any interruption is occurred
     */
    @Override
    public EmployeeDTO findUserByStaffID(String staffID) throws UserNotFoundException {
        userServiceLogger.info("UserService.findUserByStaffID() :: method call ---- STARTS");

        EmployeeDTO employee;
        Employee userFoundFromDB;

        try {
            userFoundFromDB = userRepository.findByStaffID(staffID);

            if (userFoundFromDB == null) {
                userServiceLogger.debug("UserService.findUserByStaffID() :: No user found with given staff ID = {}", staffID);

                return null;
            }

            employee = modelMapper.map(userFoundFromDB, EmployeeDTO.class);

        } catch (Exception exception) {
            userServiceLogger.info("UserService.findUserByStaffID() :: method call ---- FAILED");

            throw new UserNotFoundException(propertiesConfig.getProperty(ApplicationProperties.Messages.UNABLE_TO_FIND_USER.getValue()), exception.getMessage());
        }

        userServiceLogger.info("UserService.findUserByStaffID() :: method call ---- ENDS");

        return employee;
    }

    /**
     * Finds user given their email address
     *
     * @param email "abc@gmail.com"
     * @return EmployeeDTO
     * @throws UserNotFoundException is thrown if any interruption is occurred
     */
    @Override
    public EmployeeDTO findUserByEmail(String email) throws UserNotFoundException {
        userServiceLogger.info("UserService.findUserByEmail() :: method call ---- STARTS");

        EmployeeDTO employee;
        Employee userFoundFromDB;

        try {
            userFoundFromDB = userRepository.findByEmail(email);

            if (userFoundFromDB == null) {
                userServiceLogger.debug("UserService.findUserByEmail() :: No user found with given email = {}", email);

                return null;
            }

            employee = modelMapper.map(userFoundFromDB, EmployeeDTO.class);

        } catch (Exception exception) {
            userServiceLogger.info("UserService.findUserByEmail() :: method call ---- FAILED");

            throw new UserNotFoundException(propertiesConfig.getProperty(ApplicationProperties.Messages.UNABLE_TO_FIND_USER.getValue()), exception.getMessage());
        }

        userServiceLogger.info("UserService.findUserByEmail() :: method call ---- ENDS");

        return employee;
    }

    private EmployeeDTO copyEmployeePropertiesFromEntityToDTO(Employee employeeEntity) {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        if (employeeEntity != null) {
            BeanUtils.copyProperties(employeeEntity, employeeDTO);
            employeeDTO.setRole(copyRolePropertiesFromEntityToDTO(employeeEntity.getRole()));
            employeeDTO.setCreatedDate(employeeEntity.getCreatedDate() != null ? employeeEntity.getCreatedDate().toString() : null);
            employeeDTO.setModifiedDate(employeeEntity.getModifiedDate() != null ? employeeEntity.getModifiedDate().toString() : null);
        }

        return employeeDTO;

    }

    private RoleDTO copyRolePropertiesFromEntityToDTO(Role roleEntity) {
        RoleDTO roleDTO = new RoleDTO();

        if (roleEntity != null) {
            roleDTO.setID(roleEntity.getID());
            roleDTO.setCreatedDate(!StringUtils.isEmpty(roleEntity.getCreatedDate()) ? roleEntity.getCreatedDate().toString() : null);
            roleDTO.setCreatedBy(roleEntity.getCreatedBy());
            roleDTO.setModifiedDate(!StringUtils.isEmpty(roleEntity.getModifiedDate()) ? roleEntity.getModifiedDate().toString() : null);
            roleDTO.setModifiedBy(roleEntity.getModifiedBy());
            roleDTO.setName(roleEntity.getName());

        }

        return roleDTO;

    }

}