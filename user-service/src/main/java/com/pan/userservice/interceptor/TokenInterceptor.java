package com.pan.userservice.interceptor;

import com.pan.common.dto.UserToken;
import com.pan.common.execption.TokenExecption;
import com.pan.common.utils.JwtUtils;
import com.pan.userservice.config.RedisUtil;
import io.jsonwebtoken.Claims;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@Data
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisUtil redisUtil;
    private static String tokenName = "token";
    private static String redisTokenKeyPrefix = "token_";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(tokenName);
        if (StringUtils.isEmpty(token)){
            throw new TokenExecption("没传token");
        }

        Claims claims = jwtUtils.verifyAndGetClaimsByToken(token);
        if (claims==null){
            throw new TokenExecption("token失效或不存在");
        }

        String loginAccount = (String) claims.get("loginAccount");

        String saveToken = redisUtil.get(redisTokenKeyPrefix+loginAccount);
        if (StringUtils.isEmpty(saveToken)||!saveToken.equals(token)){
            throw new TokenExecption("token过期或不存在");
        }

        log.info("=======当前登录用户：{}=========",loginAccount);
        UserToken userToken = new UserToken();
        userToken.setLoginAccount(loginAccount);
        userToken.setToken(token);

        Long expireTime = redisUtil.getExpireTime(redisTokenKeyPrefix + loginAccount);
        //token有效期低于60s 则对token进行续期
        if (expireTime<60){
            log.info("======token[{}]续期，[{}s]======",token,jwtUtils.getTOKEN_EXPIRE());
            redisUtil.setEx(redisTokenKeyPrefix+loginAccount,token,jwtUtils.getTOKEN_EXPIRE());
        }else {
            log.info("======token[{}]，有效期[{}s]======",token,expireTime);
        }
        TokenThreadLocal.set(userToken);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TokenThreadLocal.remove();
    }
}
