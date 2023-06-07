package com.pan.orderservice.feign;

import com.pan.common.entity.RespenseBean;
import com.pan.common.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(value = "userService",
        fallbackFactory = UserServiceFeignFallbackFactory.class,
        configuration = FeignRequestInterceptor.class
)
public interface UserServiceFeign {
    @GetMapping("/user/test/{id}")
    RespenseBean hello(@PathVariable Integer id) ;

    @PostMapping("/user/selectAll")
    RespenseBean selectAll(@RequestHeader("token") String token);
}
