package com.pan.userservice.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.github.pagehelper.PageInfo;
import com.pan.common.dto.UserSearchDTO;
import com.pan.common.dto.UserToken;
import com.pan.common.entity.ResponseBean;
import com.pan.common.entity.User;
import com.pan.common.service.UserService;
import com.pan.userservice.common.ProjectProperty;
import com.pan.userservice.entity.MongodbUser;
import com.pan.userservice.mongodbdao.MongodbUserRepository;
import io.minio.MinioClient;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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

    @Autowired
    private MongodbUserRepository mongodbUserRepository;

    @GetMapping("/test/{id}")
    public ResponseBean hello(@PathVariable Integer id) {
        User user1 = new User();
        user1.setName("11");
        user1.setAge(211);
        User user = userService2.insert(user1);
        return ResponseBean.success(user);
    }
    @PostMapping("/add")
    public ResponseBean add(@RequestBody User user) {
        User insert = userService.insert(user);
        return ResponseBean.success(insert);
    }


    /**
     * 查询用户list
     * @return
     */
    @PostMapping("/selectAll")
    public ResponseBean selectAll() throws InterruptedException {
//        TimeUnit.SECONDS.sleep(3);
        RLock lock = redisson.getLock("all");
        boolean tryLock = lock.tryLock();
        if (tryLock){
            try {
                List<User> list = userService.selectAll();

                return ResponseBean.success(list);
            }finally {
                lock.unlock();
            }
        }
        return ResponseBean.success(null);
    }

    /**
     * 分页查询用户
     * @param userSearchDTO
     * @return
     */
    @PostMapping("/selectAllPage")
    public ResponseBean selectAllPage(String name, @RequestBody UserSearchDTO userSearchDTO) {
        PageInfo page = userService.selectAllPage(userSearchDTO);
        return ResponseBean.success(page);
    }

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public ResponseBean login(@Validated @RequestBody User user) {
        UserToken userDto = userService.login(user);
        MongodbUser mongodbUser = new MongodbUser();
        mongodbUser.setContent("评论");
        mongodbUser.setTime(LocalDateTime.now());
        mongodbUser.setAge(22);


        MongodbUser insert = mongodbUserRepository.insert(mongodbUser);
        return ResponseBean.success(userDto, "登录成功");
    }

    /**
     * 退出
     * @param user
     * @return
     */
    @PostMapping("/logout")
    public ResponseBean logout() {
        UserToken userDto = userService.logout(null);
        return ResponseBean.success(userDto, "退出成功");
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
    public ResponseBean register(@RequestBody User user) {
        User returnUser = userService.register(user);
        return ResponseBean.success(returnUser);
    }
}
