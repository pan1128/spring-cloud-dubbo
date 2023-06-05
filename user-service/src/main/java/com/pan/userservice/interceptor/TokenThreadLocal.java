package com.pan.userservice.interceptor;

import com.pan.common.dto.UserToken;
import org.springframework.core.NamedThreadLocal;

public class TokenThreadLocal {

    private final static NamedThreadLocal<UserToken> userTokenNamedThreadLocal = new NamedThreadLocal<>("用户信息");


    public static void set(UserToken userToken){
        userTokenNamedThreadLocal.set(userToken);
    }

    public static UserToken get(){
        return userTokenNamedThreadLocal.get();
    }

    public static void remove() {
        userTokenNamedThreadLocal.remove();
    }
}
