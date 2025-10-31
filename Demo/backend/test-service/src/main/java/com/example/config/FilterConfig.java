package com.example.config;

import com.example.filter.JwtValidationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JwtValidationFilter> jwtFilter() {
        FilterRegistrationBean<JwtValidationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtValidationFilter());
        registrationBean.addUrlPatterns("/test/*"); // Apply to test endpoints only
        return registrationBean;
    }
}