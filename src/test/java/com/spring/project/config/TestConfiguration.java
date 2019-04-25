package com.spring.project.config;

import com.spring.project.manager.HomePageManager;
import com.spring.project.service.HomePageService;
import com.spring.project.utility.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

/**
 * This config class is required when we are using @WebMvcTest, in order to create beans, scanning Annotated
 * classes, auto-wiring repositories, and executing sql queries.
 */
@Configuration
@EnableJpaRepositories(basePackages = {"com.spring.project"})
@ComponentScan(basePackages = {"com.spring.project"})
@Sql(value = {"classpath:import.sql"})
@PropertySource(value = {"classpath:spring-wmt.properties"})
@Import({PasswordUtil.class, BCryptPasswordEncoder.class})
public class TestConfiguration {

    private final Environment environment;
    private final DataSource dataSource;
    private final LocalContainerEntityManagerFactoryBean entityManagerFactory;

    /**
     * <p>
     * We used @Lazy to make sure dependencies are available at the time IOC injects them
     * and to avoid circular reference issue
     * </p>
     *
     * @param environment          Environment
     * @param dataSource           DataSource
     * @param entityManagerFactory LocalContainerEntityManagerFactoryBean
     */
    @Lazy
    @Autowired
    public TestConfiguration(final Environment environment, final DataSource dataSource, final LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        this.environment = environment;
        this.dataSource = dataSource;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public HomePageManager homePageManager() {
        return new HomePageService();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("database.driverClassName")));
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
        hibernateProperties.put("hibernate.ddl-auto", Objects.requireNonNull(environment.getProperty("property.hibernate.ddl-auto")));
        hibernateProperties.put("hibernate.dialect", Objects.requireNonNull(environment.getProperty("property.hibernate.dialect")));
        hibernateProperties.put("hibernate.show_sql", Objects.requireNonNull(environment.getProperty("property.hibernate.show_sql")));
        hibernateProperties.put("hibernate.hbm2ddl.auto", Objects.requireNonNull(environment.getProperty("property.hibernate.hbm2ddl.auto")));
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