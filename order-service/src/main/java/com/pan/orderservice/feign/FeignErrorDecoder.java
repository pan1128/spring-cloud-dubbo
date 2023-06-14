package com.pan.orderservice.feign;

import com.pan.common.execption.TokenExecption;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        InputStream inputStream = null;
        String bodyString = "";
        try {
            inputStream = response.body().asInputStream();
            byte[] bytes = Util.toByteArray(inputStream);
            bodyString= new String(bytes, StandardCharsets.UTF_8);

        } catch (Exception e) {

        }

        return new TokenExecption(bodyString);
    }
}
