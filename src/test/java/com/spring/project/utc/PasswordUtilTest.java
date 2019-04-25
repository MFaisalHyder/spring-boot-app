package com.spring.project.utc;

import com.spring.project.utility.PasswordUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DisplayName(value = "PassWordUtilityTest")
@Import({PasswordUtil.class, BCryptPasswordEncoder.class})
class PasswordUtilTest {

    private static final String actualPassword = "12345ABC";

    //Either Import classes that include Bean or define Bean definitions to load them up in container for test cases
    /*
    @Configuration
    static class Config {

        @Bean
        public PasswordUtil passwordUtil() {

            return new PasswordUtil();
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {

            return new BCryptPasswordEncoder();
        }
    }
    */

    private final PasswordUtil passwordUtil;

    @Autowired
    public PasswordUtilTest(final PasswordUtil passwordUtil) {
        this.passwordUtil = passwordUtil;
    }

    @Test
    void testPasswordUtility() {
        String encryptedPassword = passwordUtil.encryptPassword(actualPassword);

        assertTrue(passwordUtil.doesPasswordMatch(actualPassword, encryptedPassword));

        List<String> passwords = new ArrayList<>(Arrays.asList("12345abc", "12345def", "12345ghi", "12345jkl",
                "12345mno", "12345pqr", "12345stu", "12345vwx", "12345yza", "12345bcd"));

        for (String rawPassword : passwords) {
            System.out.println(passwordUtil.encryptPassword(rawPassword));
            assertTrue(passwordUtil.doesPasswordMatch(rawPassword, passwordUtil.encryptPassword(rawPassword)));
        }
    }

}