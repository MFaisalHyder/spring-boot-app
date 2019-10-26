package com.spring.project.config;

import com.spring.project.constant.ApplicationConstants;
import org.modelmapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationConfig {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(ApplicationConstants.DATE_FORMAT);
    private final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern(ApplicationConstants.DATE_TIME_FORMAT);

    /*
    @Bean
    public FormattingConversionService conversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(false);

        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setDateFormatter(dateFormatter);
        registrar.setDateTimeFormatter(dateTimeFormatter);
        registrar.registerFormatters(conversionService);

        return conversionService;
    }
    */

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Provider<LocalDateTime> localDateProvider = new AbstractProvider<LocalDateTime>() {
            @Override
            public LocalDateTime get() {

                return LocalDateTime.now();
            }
        };

        Converter<String, LocalDateTime> toStringDate = new AbstractConverter<String, LocalDateTime>() {
            @Override
            protected LocalDateTime convert(String source) {

                return !StringUtils.isEmpty(source) ? LocalDateTime.parse(source, dateTimeFormatter) : null;
            }
        };

        modelMapper.createTypeMap(String.class, LocalDateTime.class);
        modelMapper.addConverter(toStringDate);

        modelMapper.getTypeMap(String.class, LocalDateTime.class).setProvider(localDateProvider);

        return modelMapper;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

}