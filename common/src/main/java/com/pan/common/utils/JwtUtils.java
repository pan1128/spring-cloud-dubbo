package com.pan.common.utils;

import com.pan.common.dto.UserToken;
import com.pan.common.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RefreshScope
@Slf4j
@Data
public class JwtUtils {
    @Autowired
    private Environment environment;
    public static String HEADER_KEY = "Authorization";
    private static String SECRET = "qif123.,adminSecret";
//    public static long MAX_EXPPIRE = 94608000L;
//    public static long TOKEN_EXPIRE = 7200L;
    public static long TOKEN_DB_REFRESH = 300L;


    @Value("${token.expire:7200}")
    private  Long TOKEN_EXPIRE;
    @Value("${token.max-expire:20}")
    private  Long MAX_EXPPIRE;
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

    public  String generateToken(User user, Long modifyTime) {
        if (user == null) {
            throw new RuntimeException("登录信息有误");
        } else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("loginAccount",user.getLoginAccount());
            log.info("==========MAX_EXPPIRE:[{}]======",MAX_EXPPIRE);
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

    public  Claims verifyAndGetClaimsByToken(String token) {
        try {
            return (Claims) Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (Exception var2) {
            log.error("verify token error:[{}] ", var2.getMessage());
            return null;
        }
    }
}
