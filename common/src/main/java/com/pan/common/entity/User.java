package com.pan.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2023-05-31 21:37:23
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 918809244121932190L;
    
    private Integer id;
    
    private String name;
    
    private Integer age;

    //登录账号密码
    private String password;

    //登录账号
    private String loginAccount;

    //账号类型
    private String accountType;

    //头像
    private String pic;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}

