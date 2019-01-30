package com.spring.project.config;

import com.spring.project.constant.ApplicationConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

@Configuration
@PropertySource(ignoreResourceNotFound = true, value = "classpath:app_messages.properties")
public class PropertiesConfig {

    private static final Logger propertiesConfigLogger = LogManager.getLogger(PropertiesConfig.class);

    @Autowired
    private Environment environment;

    public String getProperty(String propertyKey) {

        if (StringUtils.isEmpty(propertyKey)) {
            propertiesConfigLogger.error("PropertiesConfig.getProperty() :: propertyKey is NULL");

            return null;
        }

        String propertyValue = ApplicationConstants.GeneralConstants.EMPTY_STRING.getValue();

        try {
            propertyValue = environment.getProperty(propertyKey);

        } catch (Exception exception) {
            propertiesConfigLogger.error("PropertiesConfig.getProperty() :: Unable to find value for the key = '{}'", propertyKey);
            propertiesConfigLogger.error(exception);
        }

        return propertyValue;
    }

}