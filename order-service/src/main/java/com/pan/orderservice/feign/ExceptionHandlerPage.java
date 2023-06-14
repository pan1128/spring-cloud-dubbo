package com.pan.orderservice.feign;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pan.common.entity.RespenseBean;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 捕获sentinel异常类 自定义返回response
 */
@Component
public class ExceptionHandlerPage implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        // Return 429 (Too Many Requests) by default.
//        response.setStatus(429);
        RespenseBean respenseBean = RespenseBean.fail("ExceptionHandlerPage-调用订单服务失败");
        PrintWriter out = response.getWriter();
        out.print(new ObjectMapper().writeValueAsString(respenseBean));
        out.flush();
        out.close();
    }
}
