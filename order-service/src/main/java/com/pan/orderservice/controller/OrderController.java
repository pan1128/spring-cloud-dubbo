package com.pan.orderservice.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.pan.common.entity.User;
import com.pan.common.service.UserService;
import com.pan.orderservice.common.ProjectProperty;
import com.pan.orderservice.common.RespenseBean;
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
    public RespenseBean hello(@PathVariable Integer id) {
        User user = userService.queryById(id);
        return RespenseBean.success(user);
    }
    @GetMapping("/testFeign/{id}")
    @GlobalTransactional
    public RespenseBean testFeign(@PathVariable Integer id) {
        RespenseBean respenseBean = userServiceFeign.hello(id);
        return RespenseBean.success(respenseBean);
    }

    @PostMapping("/addOrder")
    @GlobalTransactional
    public RespenseBean add(@RequestBody Order order) {
        Order insert = orderService.insert(order);
        User user = new User();
        user.setName(RandomUtils.nextInt()+"");
        userService.insert(user);
        return RespenseBean.success(insert);
    }
    //注册接口

    @PostMapping("/login")
    public RespenseBean login(@RequestBody User user) {
        User userDto = userService.selectOneByNameUser(user);
        if (userDto ==null){
            return RespenseBean.fail("账号不存在，登录失败！");
        }
        StpUtil.login(userDto.getId());
        return RespenseBean.success(StpUtil.getTokenInfo(), "登录成功");
    }
    @RequestMapping("isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }
    //登录接口

    //验证码接口
}
