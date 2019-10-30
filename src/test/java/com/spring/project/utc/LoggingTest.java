package com.spring.project.utc;

import com.spring.project.config.Log4j2Config;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName(value = "LoggingTest")
class LoggingTest {

    @Test
    @DisplayName("testLogging")
    void testLogging() {
        Log4j2Config log4j2Config = new Log4j2Config();
        for (int i = 0; i < 10; i++) {
            log4j2Config.performLogging();
        }

    }

}