package com.pan.common.dto;

import lombok.Data;

@Data
public class UserToken {
    //登录账号
    private String loginAccount;

    //账号类型
    private String accountType;

    //头像
    private String pic;

    private String token;
}
