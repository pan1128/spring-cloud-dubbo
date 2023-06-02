package com.pan.userservice.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.pan.common.entity.User;
import com.pan.common.service.UserService;
import com.pan.userservice.common.ProjectProperty;
import com.pan.userservice.common.RespenseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@RestController
public class UserController {

    @Autowired
    private ProjectProperty projectProperty;

    @Autowired(required = false)
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Autowired
    @Qualifier("user2ServiceImpl")
    private UserService userService2;
    @Value("${anliang.name}")
    private String name;

    @GetMapping("/test/{id}")
    public RespenseBean hello(@PathVariable Integer id) {
        User user1 = new User();
        user1.setName("11");
        user1.setAge(211);
        User user = userService2.insert(user1);
        return RespenseBean.success(user);
    }
    @PostMapping("/add")
    public RespenseBean add(@RequestBody User user) {
        User insert = userService.insert(user);
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
