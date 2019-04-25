package com.spring.project.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Import({BCryptPasswordEncoder.class})
public class PasswordUtil {

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public PasswordUtil(final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    public String encryptPassword(String rawPassword) {

        return passwordEncoder.encode(rawPassword);

    }

    public boolean doesPasswordMatch(String rawPassword, String encryptedPassword) {
        if (!StringUtils.isEmpty(rawPassword) && !StringUtils.isEmpty(encryptedPassword)) {

            return passwordEncoder.matches(rawPassword, encryptedPassword);
        }

        return false;

    }

}