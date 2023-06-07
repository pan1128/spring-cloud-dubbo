package com.pan.orderservice.feign;

import com.pan.common.entity.RespenseBean;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class UserServiceFeignFallbackFactory implements FallbackFactory<UserServiceFeign> {
    @Override
    public UserServiceFeign create(Throwable cause) {
        return new UserServiceFeign() {
            @Override
            public RespenseBean hello(Integer id) {

                log.error(cause.getMessage(),cause);
                return RespenseBean.fail("调用用户服务失败");
            }

            @Override
            public RespenseBean selectAll(String token) {
                log.error(cause.getMessage(),cause);
                return RespenseBean.fail("调用用户服务失败");
            }
        };
    }
}
