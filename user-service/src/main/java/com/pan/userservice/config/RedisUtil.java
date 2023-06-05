package com.pan.userservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    public void set(String key,Object value){

        redisTemplate.opsForValue().set(key, value);
    }
    public void setEx(String key,Object value,long expireTime){
        redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
    }
    public String get(String key){

        return (String)redisTemplate.opsForValue().get(key);
    }

    public void remove(String key){
          redisTemplate.delete(key);
    }

    public Long getExpireTime(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
}
