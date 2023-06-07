package com.pan.userservice.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class FilterConfig {


    @Bean
    public FilterRegistrationBean filterRegistration(XssFilter xssFilter) {

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

        filterRegistrationBean.setFilter(xssFilter);
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/*"));
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

}
