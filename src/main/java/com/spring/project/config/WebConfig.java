package com.spring.project.config;
/*
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
*/

/**
 * <b>IMPORTANT<b/>
 * <br/>
 * <p>
 * We don't want CORS[Cross Origin Resource Sharing] to be enabled.
 * <br/>
 * In order to enable it we need to override public void addCorsMappings(CorsRegistry registry)
 * <p/>
 */
//@Configuration
//@EnableWebMvc
public class WebConfig { //implements WebMvcConfigurer {

    //@Override
    //public void addCorsMappings(CorsRegistry registry) {

    //this will allow all resources with default response headers
    //registry.addMapping("/**");

    //this will allow all resources with default response headers for the given HTTP calls
        /*
        registry.addMapping("/**")
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
        */

    //this will allow the particular mapping to be shared across origins
        /*
        registry.addMapping("/admin/**")
                .allowedOrigins("http://localhost:8080", "http://localhost:7001")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowedHeaders("X-Auth-Token", "Content-Type")
                .allowCredentials(false)
                .maxAge(4800);
        */

    //}

}