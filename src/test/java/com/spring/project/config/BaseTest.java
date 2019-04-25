package com.spring.project.config;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // This is useful when we don't want to create an instance of Test class
                                                // for every test in class. But this should be used when tests
                                                // are in isolation i.e. they don't rely on data or fields modified
                                                // by other methods which may result in intermittent results when test
                                                // cases are executed.
public abstract class BaseTest {

}