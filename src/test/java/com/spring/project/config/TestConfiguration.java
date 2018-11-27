package com.spring.project.config;

import com.spring.project.manager.HomePageManager;
import com.spring.project.service.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * This config class is required when we are using @WebMvcTest, in order to create beans, scanning Annotated
 * classes, auto-wiring repositories, and executing sql queries.
 */
@Configuration
@EnableJpaRepositories(basePackages = {"com.spring.project"})
@ComponentScan(basePackages = {"com.spring.project.controller"})
@Sql(value = {"classpath:import.sql"})
@PropertySource(value = {"classpath:spring-wmt.properties"})
public class TestConfiguration {

    @Autowired
    private Environment environment;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;

    @Bean
    public HomePageManager homePageManager() {
        return new HomePageService();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("database.driverClassName"));
        dataSource.setUrl(environment.getProperty("database.url"));
        dataSource.setUsername(environment.getProperty("database.username"));
        dataSource.setPassword(environment.getProperty("database.password"));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan("com.spring.project");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

        // Hibernate properties
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.ddl-auto", environment.getProperty("property.hibernate.ddl-auto"));
        hibernateProperties.put("hibernate.dialect", environment.getProperty("property.hibernate.dialect"));
        hibernateProperties.put("hibernate.show_sql", environment.getProperty("property.hibernate.show_sql"));
        hibernateProperties.put("hibernate.hbm2ddl.auto", environment.getProperty("property.hibernate.hbm2ddl.auto"));
        entityManagerFactory.setJpaProperties(hibernateProperties);

        return entityManagerFactory;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());

        return transactionManager;
    }

    /**
     * PersistenceExceptionTranslationPostProcessor is a bean post processor
     * which adds an advisor to any bean annotated with Repository so that any
     * platform-specific exceptions are caught and then rethrown as one
     * Spring's unchecked data access exceptions (i.e. a subclass of
     * DataAccessException).
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {

        return new PersistenceExceptionTranslationPostProcessor();
    }

}