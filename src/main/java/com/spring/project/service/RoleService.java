package com.spring.project.service;

import com.spring.project.config.PropertiesConfig;
import com.spring.project.constant.ApplicationProperties;
import com.spring.project.dto.RoleDTO;
import com.spring.project.entity.Role;
import com.spring.project.exception.GeneralException;
import com.spring.project.exception.RoleNotFoundException;
import com.spring.project.manager.RoleManager;
import com.spring.project.repository.RoleRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements RoleManager {

    private static final Logger roleServiceLogger = LogManager.getLogger(RoleService.class);

    private final RoleRepository roleRepository;
    private final PropertiesConfig propertiesConfig;

    public RoleService(final RoleRepository roleRepository, final PropertiesConfig propertiesConfig) {
        this.roleRepository = roleRepository;
        this.propertiesConfig = propertiesConfig;

    }

    @Override
    public RoleDTO findByName(String name) throws RoleNotFoundException {
        roleServiceLogger.info("RoleService.findByName() :: method call ---- STARTS");

        Role roleFoundInDB;
        RoleDTO roleDTO;

        try {
            roleFoundInDB = roleRepository.findByName(name);

            if (roleFoundInDB == null) {
                roleServiceLogger.debug("RoleService.findByName() :: No role found in DB for given role name = {}", name);

                throw new RoleNotFoundException(propertiesConfig.getProperty(ApplicationProperties.Messages.NO_ROLE_FOUND.getValue()));
            }

            roleDTO = new RoleDTO();

            BeanUtils.copyProperties(roleDTO, roleFoundInDB);

        } catch (Exception exception) {
            roleServiceLogger.error("RoleService.findByName() ::  Some error occurred while finding role ---- FAILED");

            throw new GeneralException(propertiesConfig.getProperty(ApplicationProperties.Messages.REQUEST_NOT_PROCESSED.getValue()), exception.getMessage());

        }

        roleServiceLogger.info("RoleService.findByName() :: method call ---- ENDS");

        return roleDTO;
    }

}