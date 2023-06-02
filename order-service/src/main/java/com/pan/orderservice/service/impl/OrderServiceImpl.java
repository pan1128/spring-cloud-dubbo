package com.pan.orderservice.service.impl;

import com.pan.common.service.UserService;
import com.pan.orderservice.dao.OrderDao;
import com.pan.orderservice.entity.Order;
import com.pan.orderservice.service.OrderService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * (Order)表服务实现类
 *
 * @author makejava
 * @since 2023-06-01 10:30:56
 */
//@Service("orderService")
@DubboService
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;

    @DubboReference
    private UserService userService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Order queryById(Integer id) {
        return this.orderDao.queryById(id);
    }


    /**
     * 新增数据
     *
     * @param order 实例对象
     * @return 实例对象
     */
    @Override
    public Order insert(Order order) {
        this.orderDao.insert(order);

        /*User user = new User();
        user.setName(RandomUtils.nextInt()+"");
        userService.insert(user);*/
        return order;
    }

    /**
     * 修改数据
     *
     * @param order 实例对象
     * @return 实例对象
     */
    @Override
    public Order update(Order order) {
        this.orderDao.update(order);
        return this.queryById(order.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.orderDao.deleteById(id) > 0;
    }
}
