package com.pan.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * spring Resource 工具类
 */
@Slf4j
public class ResourceUtils {

    /**
     *  获取url InputStream http:// file://
     * @param url
     * @return
     */
    public static InputStream getInputStream(String url) {
        InputStream  is = null;
        try {
            UrlResource urlResource = new UrlResource(url);
            is= urlResource.getInputStream();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return is;
    }

    public static void main(String[] args) throws IOException {
        String url = "file:D:\\qif\\NetSDKInit.txt";
        InputStream is = getInputStream(url);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        StreamUtils.copy(is, outputStream);

        System.out.println(new String(outputStream.toByteArray(), StandardCharsets.UTF_8));
    }
}
