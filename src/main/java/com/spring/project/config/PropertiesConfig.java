package com.spring.project.config;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

@Configuration
@PropertySource(ignoreResourceNotFound = true, value = "classpath:app_messages.properties")
public class PropertiesConfiguration {

    private static final Logger userServiceLogger = LogManager.getLogger(PropertiesConfiguration.class);
    @Autowired
    private Environment environment;

    public static String getProperty(String propertyKey) {
        if (StringUtils.isEmpty(propertyKey)) {

        }
        try {

        } catch () {

        }

    }

}