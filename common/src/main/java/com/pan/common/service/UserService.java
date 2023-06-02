package com.pan.common.service;


import com.pan.common.entity.User;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2023-05-31 21:37:25
 */
public interface UserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User queryById(Integer id);


    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User insert(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User update(User user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 根据名称查询用户
     * @param user
     * @return
     */
    User selectOneByNameUser(User user);
}
