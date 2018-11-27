package com.spring.project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class Application {

    private static final Logger logger = LogManager.getLogger(Application.class);

    public static void main(String... array) {
        SpringApplication.run(Application.class, array);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext applicationContext) {
        return args -> {
            //System.out.println("Let's inspect the beans provided by Spring Boot:");

            logger.info("**********-------------Application-MAIN-------------**********");
            logger.debug("DEBUG");
            logger.info("INFO");
            logger.warn("WARNING");
            logger.error("ERROR");
            logger.fatal("FATAL");
            logger.info("**********-------------Application-MAIN-------------**********");

            String[] beanNames = applicationContext.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                //System.out.println(beanName);
            }
        };
    }
}