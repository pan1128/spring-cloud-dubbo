package com.pan.userservice.controller;

import com.pan.common.entity.ResponseBean;
import io.minio.*;
import lombok.SneakyThrows;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@RestController
public class MinioController {


    @Resource
    private MinioClient minioClient;

    /**
     * 上传文件
     * @param bucketName
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @SneakyThrows
    public ResponseBean upload(String bucketName, @RequestParam("file") MultipartFile file){
        BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket(bucketName).build();
        boolean bucketExists = minioClient.bucketExists(bucketExistsArgs);
        String fileName = file.getOriginalFilename();
        if (!bucketExists){
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        int available = file.getInputStream().available();
        String contentType = file.getContentType();

        minioClient.putObject(
                PutObjectArgs.builder()
                        .stream(file.getInputStream(),available,-1)
                        .bucket(bucketName)
                        .object(fileName)
                        .contentType(contentType)
                        .build()
        );
        return ResponseBean.success(null);
    }


    /**
     * 下载文件
     * @param bucketName
     * @param fileName
     */
    @SneakyThrows
    @RequestMapping("/download")
    public void download(String bucketName, String fileName) {
        BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket(bucketName).build();
        boolean bucketExists = minioClient.bucketExists(bucketExistsArgs);
        if (!bucketExists){
            throw new RuntimeException("bucket不存在");
        }
        GetObjectResponse getObjectResponse = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .build()
        );
        System.out.println(1);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletResponse response = requestAttributes.getResponse();
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                ContentDisposition.builder("attachment")
                        .filename(fileName, StandardCharsets.UTF_8)
                        .build()
                        .toString()
        );
        StreamUtils.copy(getObjectResponse, response.getOutputStream());
    }

    /**
     * 删除文件
     * @param bucketName
     * @param fileName
     */
    @SneakyThrows
    @RequestMapping("/delete")
    public void delete(String bucketName, String fileName) {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
    }
}
