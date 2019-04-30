package com.spring.project.config;

import com.spring.project.utility.PasswordUtil;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class) //This is not mandatory
@SpringBootTest
@AutoConfigureMockMvc(secure = false) // Secure false is required to by pass security for Test Cases
@ContextConfiguration //This is also not mandatory just to remove annoying warning, i added it
//@Sql({"classpath:import.sql"}) //Not needed in Spring boot, if added then Integrity error will arise
//@Import({PasswordUtil.class, BCryptPasswordEncoder.class}) Not required as we declared Beans explicitly
public abstract class BaseTest_SBT {

}