package com.pan.common.utils;

import com.pan.common.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;

public class JwtUtil {

    private static final String SECRET_KEY = "qif123.,adminSecret"; // 密钥
    private static final long EXPIRATION_TIME = 86400000L;//*365*10; // 过期时间，这里设置为1天（单位：毫秒）

    public static String generateToken(User user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        HashMap<String, Object> map = new HashMap<>();
        map.put("loginAccount",user.getLoginAccount());

        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setClaims(map)
                .compact();

        return token;
    }
}
