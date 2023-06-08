package com.pan.userservice.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.github.pagehelper.PageInfo;
import com.pan.common.dto.UserSearchDTO;
import com.pan.common.dto.UserToken;
import com.pan.common.entity.RespenseBean;
import com.pan.common.entity.User;
import com.pan.common.service.UserService;
import com.pan.userservice.common.ProjectProperty;
import io.minio.MinioClient;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RefreshScope
@RestController
public class UserController {

    @Autowired
    private ProjectProperty projectProperty;

    @Autowired(required = false)
    private UserService userService;

    @Autowired
    private MinioClient minioClient;

    @Resource
    private Redisson redisson;

    @Autowired
    private UserService userService2;

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


    /**
     * 查询用户list
     * @return
     */
    @PostMapping("/selectAll")
    public RespenseBean selectAll() throws InterruptedException {
//        TimeUnit.SECONDS.sleep(3);
        RLock lock = redisson.getLock("all");
        boolean tryLock = lock.tryLock();
        if (tryLock){
            try {
                List<User> list = userService.selectAll();

                return RespenseBean.success(list);
            }finally {
                lock.unlock();
            }
        }
        return RespenseBean.success(null);
    }

    /**
     * 分页查询用户
     * @param userSearchDTO
     * @return
     */
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
    public RespenseBean login(@Validated @RequestBody User user) {
        UserToken userDto = userService.login(user);
        return RespenseBean.success(userDto, "登录成功");
    }

    /**
     * 退出
     * @param user
     * @return
     */
    @PostMapping("/logout")
    public RespenseBean logout() {
        UserToken userDto = userService.logout(null);
        return RespenseBean.success(userDto, "退出成功");
    }

    /**
     * 用户是否登录
     * @return
     */
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
