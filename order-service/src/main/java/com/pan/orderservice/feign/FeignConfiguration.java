package com.pan.orderservice.feign;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * feign 配置类 @Configuration全局有效
 */
public class FeignConfiguration {

    @Bean
    public RequestInterceptor feignRequestInterceptor() {
        return new FeignRequestInterceptor();
    }
}
