package com.spring.project.config;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class) //This is not mandatory
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration //This is also not mandatory just to remove annoying warning, i added it
//@Sql({"classpath:import.sql"}) //Not needed in Spring boot, if added then Integrity error will arise
public class BaseTest_SBT {

}