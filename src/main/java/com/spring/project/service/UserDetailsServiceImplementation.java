package com.spring.project.service;

import com.spring.project.entity.User;
import com.spring.project.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    private static final Logger userDetailsServiceImplementationLogger = LogManager.getLogger(UserDetailsServiceImplementation.class);
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImplementation(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            userDetailsServiceImplementationLogger.error("UserDetailsServiceImplementation.loadUserByUsername() :: FAILED");

            throw new UsernameNotFoundException("UserName is not passed");
        }

        User userFound = userRepository.findByEmail(username);

        if (userFound == null) {
            userDetailsServiceImplementationLogger.error("No user found with given username = {}", username);

            throw new UsernameNotFoundException("No user found with given username");
        }

        userDetailsServiceImplementationLogger.debug("UserDetailsServiceImplementation.loadUserByUsername() :: User Found");

        return userFound.currentUserDetails();
    }

}