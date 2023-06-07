package com.pan.userservice.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 防止xss攻击 过滤器
 */
@Component
//@WebFilter
public class XssFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println(1);
        filterChain.doFilter(servletRequest,servletResponse);
    }


}
