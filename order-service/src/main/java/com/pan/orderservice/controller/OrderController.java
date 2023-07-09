package com.pan.orderservice.controller;

import com.pan.common.entity.ResponseBean;
import com.pan.common.entity.User;
import com.pan.common.service.UserService;
import com.pan.orderservice.common.ProjectProperty;
import com.pan.orderservice.entity.Order;
import com.pan.orderservice.feign.UserServiceFeign;
import com.pan.orderservice.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@RestController
public class OrderController {

    @Autowired
    private ProjectProperty projectProperty;

    @DubboReference
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserServiceFeign userServiceFeign;

    @Value("${anliang.name}")
    private String name;

    @GetMapping("/test/{id}")
    public ResponseBean hello(@PathVariable Integer id) {
        User user = userService.queryById(id);
        return ResponseBean.success(user);
    }
    @GetMapping("/testFeign/{id}")
    @GlobalTransactional
    public ResponseBean testFeign(@PathVariable Integer id) {
        ResponseBean responseBean = userServiceFeign.hello(id);
        return ResponseBean.success(responseBean);
    }

    @PostMapping("/addOrder")
    @GlobalTransactional
    public ResponseBean add(@RequestBody Order order) {
        Order insert = orderService.insert(order);
        User user = new User();
        user.setName(RandomUtils.nextInt()+"");
        userService.insert(user);
        return ResponseBean.success(insert);
    }

    @PostMapping("/selectAllUser")
    public ResponseBean selectAllUser(@RequestHeader("token") String token) {
        ResponseBean responseBean = userServiceFeign.selectAll(token);
        return responseBean;
    }
}
