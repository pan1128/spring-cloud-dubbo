package com.pan.orderservice.feign;

import com.pan.common.entity.ResponseBean;
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
            public ResponseBean hello(Integer id) {

                log.error(cause.getMessage(),cause);
                return ResponseBean.fail("调用用户服务失败");
            }

            @Override
            public ResponseBean selectAll(String token) {
                log.error(cause.getMessage(),cause);
                return ResponseBean.fail("调用用户服务失败");
            }
        };
    }
}
