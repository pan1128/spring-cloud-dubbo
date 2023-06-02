package com.pan.common.utils;

import com.pan.common.dto.UserToken;
import com.pan.common.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtUtils {
    @Autowired
    private Environment environment;
    public static String HEADER_KEY = "Authorization";
    private static String SECRET = "qif123.,adminSecret";
    public static long MAX_EXPPIRE = 94608000L;
    public static long TOKEN_EXPIRE = 7200L;
    public static long TOKEN_DB_REFRESH = 300L;

    public JwtUtils() {
    }

    @PostConstruct
    public void init() {
        String maxExppire = this.environment.getProperty("jwt.expire");
        if (!StringUtils.isEmpty(maxExppire)) {
            MAX_EXPPIRE = Long.valueOf(maxExppire);
        }

        String tokenExpire = this.environment.getProperty("jwt.cache-expire");
        if (!StringUtils.isEmpty(tokenExpire)) {
            TOKEN_EXPIRE = Long.valueOf(tokenExpire);
        }

        String tokenDbRefresh = this.environment.getProperty("jwt.cache-dbrefresh");
        if (!StringUtils.isEmpty(tokenDbRefresh)) {
            TOKEN_DB_REFRESH = Long.valueOf(tokenDbRefresh);
        }

    }

    public static String generateToken(User user, Long modifyTime) {
        if (user == null) {
            throw new RuntimeException("登录信息有误");
        } else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("loginAccount",user.getLoginAccount());
            Date expireDate = new Date(modifyTime + MAX_EXPPIRE * 1000L);
            String token = Jwts.builder()
                    .setHeaderParam("typ", "JWT")
                    .setClaims(map)
                    .setExpiration(expireDate)
                    .signWith(SignatureAlgorithm.HS512, SECRET)
                    .compact();
            return token;
        }
    }

    public static Claims verifyAndGetClaimsByToken(String token) {
        try {
            return (Claims) Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (Exception var2) {
//            log.debug("verify token error:[{}] ", ExceptionUtils.getStackTrace(var2));
            return null;
        }
    }
}
