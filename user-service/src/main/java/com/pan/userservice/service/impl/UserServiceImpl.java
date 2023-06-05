package com.pan.userservice.service.impl;

import com.alibaba.nacos.common.utils.MD5Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pan.common.dto.UserSearchDTO;
import com.pan.common.dto.UserToken;
import com.pan.common.entity.User;
import com.pan.common.service.UserService;
import com.pan.common.utils.JwtUtil;
import com.pan.common.utils.JwtUtils;
import com.pan.userservice.config.RedisUtil;
import com.pan.userservice.dao.UserDao;
import com.pan.userservice.interceptor.TokenThreadLocal;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2023-05-31 21:37:25
 */
@Slf4j
@DubboService
public class UserServiceImpl implements UserService {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisUtil redisUtil;
    @Resource
    private UserDao userDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Integer id) {
        return this.userDao.queryById(id);
    }


    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User insert(User user) {
        this.userDao.insert(user);
        log.info("Seata全局事务id=================>{}", RootContext.getXID());

//        int i = 1 / 0;
        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User update(User user) {
        this.userDao.update(user);
        return this.queryById(user.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userDao.deleteById(id) > 0;
    }

    @Override
    public UserToken login(User user) {
        User byNameUser = userDao.selectOneByNameUser(user);
        if (byNameUser ==null){
            throw new RuntimeException("账号不存在，登录失败！");
        }
        String password = MD5Utils.encodeHexString(user.getPassword().getBytes(StandardCharsets.UTF_8));
        if (!byNameUser.getPassword().equals(password)){
            throw new RuntimeException("密码错误");
        }
        UserToken UserToken = new UserToken();
        BeanUtils.copyProperties(byNameUser,UserToken);

        String token = jwtUtils.generateToken(byNameUser,new Date().getTime());
        redisUtil.setEx("token_"+UserToken.getLoginAccount(),token,jwtUtils.getTOKEN_EXPIRE());
        UserToken.setToken(token);
        //密码正确登录成功
        return UserToken;

    }

    @Override
    public UserToken logout(User user) {

        redisUtil.remove("token_"+ TokenThreadLocal.get().getLoginAccount());
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User register(User user) {
        String md5Password = MD5Utils.encodeHexString(user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(md5Password);

        User searchUser = new User();
        searchUser.setLoginAccount(user.getLoginAccount());
        long count = userDao.count(searchUser);
        if (count>0){
            throw new RuntimeException("登录账户名不可用！");
        }
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userDao.insert(user);

        user.setPassword("");
        return user;
    }

    @Override
    public List<User> selectAll() {
        return userDao.selectAll();
    }

    @Override
    public PageInfo selectAllPage(UserSearchDTO userSearchDTO) {
        PageHelper.startPage(userSearchDTO.getPageNum(), userSearchDTO.getPageSize());
        List<User> list = userDao.selectAll();
        PageInfo pageInfo = new PageInfo((Page)list);
        return pageInfo;
    }
}
