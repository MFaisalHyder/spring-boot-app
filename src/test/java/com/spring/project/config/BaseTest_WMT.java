package com.spring.project.config;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;

//@RunWith(SpringRunner.class) //Since we are using Jupiter JUnit5 so we can replace this with given below
@ExtendWith(SpringExtension.class) //This annotation is also not required to add since we have already configured
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes = TestConfiguration.class)
@AutoConfigureMockMvc
public class BaseTest_WMT {

}