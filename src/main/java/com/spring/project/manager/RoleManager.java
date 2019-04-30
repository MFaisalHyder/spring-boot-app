package com.spring.project.manager;

import com.spring.project.dto.RoleDTO;
import com.spring.project.exception.RoleNotFoundException;

public interface RoleManager {

    RoleDTO findByName(String name) throws RoleNotFoundException;

}