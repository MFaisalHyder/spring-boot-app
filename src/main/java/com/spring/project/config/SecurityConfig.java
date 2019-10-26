package com.spring.project.config;

import com.spring.project.constant.ROLES;
import com.spring.project.service.UserDetailsServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImplementation userDetailsService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(UserDetailsServiceImplementation userDetailsService,
                          PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                //to make h2-console work we need to add its path as allowed
                .antMatchers("/console/**").permitAll()
                .antMatchers("/", "/greetUser", "/register", "/login", "/csrf-token").permitAll()
                //.hasAuthority requires ROLE_ prefix to be declared
                //.antMatchers("/user/**").hasAnyAuthority(ROLES.PREFIX.getValue() + ROLES.USER.getValue(), ROLES
                // .PREFIX.getValue() + ROLES.ADMIN.getValue())
                //.antMatchers("/admin/**").hasAuthority(ROLES.PREFIX.getValue() + ROLES.ADMIN.getValue()).anyRequest
                // ().authenticated()
                //.hasRole automatically adds ROLE_ prefix
                .antMatchers("/user/**").hasAnyRole(ROLES.USER.getValue(), ROLES.ADMIN.getValue())
                .antMatchers("/admin/**").hasRole(ROLES.ADMIN.getValue()).anyRequest().authenticated()
                .and().httpBasic()
                .and().formLogin().disable();

        //this will disable the csrf altogether
        //httpSecurity.csrf().disable();

        /*
            here we are allowing '/register' only to be accessed without CSRF token.
            here we will need to add those end points with POST, PUT, DELETE for which we don't require client to send
            CSRF token in request. If we don't want to allow such end points then we have to ask client layer to
            retrieve
            csrf token first using '/csrf-token' end point.
         */
        httpSecurity.csrf().ignoringAntMatchers("/register");

        //adding our custom CSRF implementation
        httpSecurity.addFilterAfter(new CsrfTokenResponseHeaderBindingFilter(), CsrfFilter.class);

        // required to make H2 console work with Spring Security
        httpSecurity.headers().frameOptions().disable();

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());

    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }

    @Override
    public void configure(WebSecurity webSecurity) {

        webSecurity.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

}