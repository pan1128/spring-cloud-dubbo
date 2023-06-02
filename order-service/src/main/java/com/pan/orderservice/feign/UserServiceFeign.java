package com.pan.orderservice.feign;

import com.pan.orderservice.common.RespenseBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "userService")
public interface UserServiceFeign {
    @GetMapping("/test/test/{id}")
    public RespenseBean hello(@PathVariable Integer id) ;
}
