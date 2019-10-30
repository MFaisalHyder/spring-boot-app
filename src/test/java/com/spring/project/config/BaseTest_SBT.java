package com.spring.project.config;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

//@ExtendWith(SpringExtension.class) // This is not mandatory
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Port is required for (Test)RestTemplate
@AutoConfigureMockMvc//(secure = false) // Secure false is required to pass security. Now we don't need to skip Security
@ContextConfiguration // This is also not mandatory just to remove annoying warning, i added it
//@Sql({"classpath:import.sql"}) // Not needed in Spring boot, if added then Integrity error will arise
//@Import({PasswordUtil.class, BCryptPasswordEncoder.class}) // Not required as we declared Beans explicitly
public abstract class BaseTest_SBT {

}