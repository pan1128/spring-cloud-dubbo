package com.pan.userservice.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.github.pagehelper.PageInfo;
import com.pan.common.dto.UserSearchDTO;
import com.pan.common.dto.UserToken;
import com.pan.common.entity.User;
import com.pan.common.service.UserService;
import com.pan.userservice.common.ProjectProperty;
import com.pan.userservice.common.RespenseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RefreshScope
@RestController
public class UserController {

    @Autowired
    private ProjectProperty projectProperty;

    @Autowired(required = false)
    private UserService userService;

    @Autowired
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
    @PostMapping("/selectAll")
    public RespenseBean selectAll() {
        List<User> list = userService.selectAll();
        return RespenseBean.success(list);
    }
    @PostMapping("/selectAllPage")
    public RespenseBean selectAllPage(@RequestBody UserSearchDTO userSearchDTO) {
        PageInfo page = userService.selectAllPage(userSearchDTO);
        return RespenseBean.success(page);
    }

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public RespenseBean login(@RequestBody User user) {
        UserToken userDto = userService.login(user);
        return RespenseBean.success(userDto, "登录成功");
    }

    @RequestMapping("isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @PostMapping("/userRegister")
    public RespenseBean register(@RequestBody User user) {
        User returnUser = userService.register(user);
        return RespenseBean.success(returnUser);
    }
}
