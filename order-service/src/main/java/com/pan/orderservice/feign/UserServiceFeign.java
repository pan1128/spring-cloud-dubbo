package com.pan.orderservice.feign;

import com.pan.common.entity.RespenseBean;
import com.pan.common.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(value = "userService",
        fallbackFactory = UserServiceFeignFallbackFactory.class,
        configuration = FeignConfiguration.class
)
public interface UserServiceFeign {
    @GetMapping("/user/test/{id}")
    RespenseBean hello(@PathVariable Integer id);

    @PostMapping("/user/selectAll")
    RespenseBean selectAll(@RequestHeader("token") String token);

/*    在feign中单独参数在方法列表中必须加上@RequestParam注解
    文件参数注解要用@RequestPart 单文件（MultipartFile file）可以不用注解 多文件（MultipartFile[] file）如果不用注解或者用@RequestParam注解 只能接受到一个文件 或者可以将文件参数封装到dto里则不需要任何注解

            在springmvc中 对于多文件@RequestParam @RequestPart都可以
            */

}
