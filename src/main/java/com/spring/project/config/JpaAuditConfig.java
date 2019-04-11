package com.spring.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {

        return new JpaAuditorAwareImpl();
    }

}

/**
 * This is an implementation of JPA Auditor Listener
 */
class JpaAuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        // If security is implemented
        /*
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) return Optional.empty();

        return Optional.of(((Employee) authentication.getPrincipal()).getEmiratesID());
        */

        return Optional.of("S776781");

    }

}