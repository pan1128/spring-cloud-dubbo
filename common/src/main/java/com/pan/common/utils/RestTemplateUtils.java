package com.pan.common.utils;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 *
 */
@Component
public class RestTemplateUtils {

    @Resource
    private RestTemplate restTemplate;

    // ----------------------------------POST-------------------------------------------------------

    /**
     * POST请求调用方式
     *
     * @param url
     *            请求URL
     * @param responseType
     *            返回对象类型
     * @return
     */
    public <T> ResponseEntity<T> post(String url, Class<T> responseType) throws RestClientException {
        return restTemplate.postForEntity(url, HttpEntity.EMPTY, responseType);
    }

    /**
     * POST请求调用方式
     *
     * @param url
     *            请求URL
     * @param requestBody
     *            请求参数体
     * @param responseType
     *            返回对象类型
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> post(String url, Object requestBody, Class<T> responseType)
        throws RestClientException {
        return restTemplate.postForEntity(url, requestBody, responseType);
    }

    /**
     * POST请求调用方式
     *
     * @param url
     *            请求URL
     * @param requestBody
     *            请求参数体
     * @param responseType
     *            返回对象类型
     * @param uriVariables
     *            URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> post(String url, Object requestBody, Class<T> responseType, Object... uriVariables)
        throws RestClientException {
        return restTemplate.postForEntity(url, requestBody, responseType, uriVariables);
    }

    /**
     * POST请求调用方式
     *
     * @param url
     *            请求URL
     * @param requestBody
     *            请求参数体
     * @param responseType
     *            返回对象类型
     * @param uriVariables
     *            URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> post(String url, Object requestBody, Class<T> responseType,
        Map<String, ?> uriVariables) throws RestClientException {
        return restTemplate.postForEntity(url, requestBody, responseType, uriVariables);
    }

    /**
     * 带请求头的POST请求调用方式
     *
     * @param url
     *            请求URL
     * @param headers
     *            请求头参数
     * @param requestBody
     *            请求参数体
     * @param responseType
     *            返回对象类型
     * @param uriVariables
     *            URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> post(String url, Map<String, String> headers, Object requestBody,
        Class<T> responseType, Object... uriVariables) throws RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return post(url, httpHeaders, requestBody, responseType, uriVariables);
    }

    /**
     * 带请求头的POST请求调用方式
     *
     * @param url
     *            请求URL
     * @param headers
     *            请求头参数
     * @param requestBody
     *            请求参数体
     * @param responseType
     *            返回对象类型
     * @param uriVariables
     *            URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> post(String url, HttpHeaders headers, Object requestBody, Class<T> responseType,
        Object... uriVariables) throws RestClientException {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, headers);
        return post(url, requestEntity, responseType, uriVariables);
    }

    /**
     * 带请求头的POST请求调用方式
     *
     * @param url
     *            请求URL
     * @param headers
     *            请求头参数
     * @param requestBody
     *            请求参数体
     * @param responseType
     *            返回对象类型
     * @param uriVariables
     *            URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> post(String url, Map<String, String> headers, Object requestBody,
        Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return post(url, httpHeaders, requestBody, responseType, uriVariables);
    }

    /**
     * 带请求头的POST请求调用方式
     *
     * @param url
     *            请求URL
     * @param headers
     *            请求头参数
     * @param requestBody
     *            请求参数体
     * @param responseType
     *            返回对象类型
     * @param uriVariables
     *            URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> post(String url, HttpHeaders headers, Object requestBody, Class<T> responseType,
        Map<String, ?> uriVariables) throws RestClientException {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, headers);
        return post(url, requestEntity, responseType, uriVariables);
    }

    /**
     * 自定义请求头和请求体的POST请求调用方式
     *
     * @param url
     *            请求URL
     * @param requestEntity
     *            请求头和请求体封装对象
     * @param responseType
     *            返回对象类型
     * @param uriVariables
     *            URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> post(String url, HttpEntity<?> requestEntity, Class<T> responseType,
        Object... uriVariables) throws RestClientException {
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType, uriVariables);
    }

    /**
     * 自定义请求头和请求体的POST请求调用方式
     *
     * @param url
     *            请求URL
     * @param requestEntity
     *            请求头和请求体封装对象
     * @param responseType
     *            返回对象类型
     * @param uriVariables
     *            URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> post(String url, HttpEntity<?> requestEntity, Class<T> responseType,
        Map<String, ?> uriVariables) throws RestClientException {
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType, uriVariables);
    }

    // ----------------------------------通用方法-------------------------------------------------------

    /**
     * 通用调用方式
     *
     * @param url
     *            请求URL
     * @param method
     *            请求方法类型
     * @param requestEntity
     *            请求头和请求体封装对象
     * @param responseType
     *            返回对象类型
     * @param uriVariables
     *            URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
        Class<T> responseType, Object... uriVariables) throws RestClientException {
        return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
    }

    /**
     * 通用调用方式
     *
     * @param url
     *            请求URL
     * @param method
     *            请求方法类型
     * @param requestEntity
     *            请求头和请求体封装对象
     * @param responseType
     *            返回对象类型
     * @param uriVariables
     *            URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
        Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
    }

    public <T> T exchangeInternal(String url, HttpMethod method, Object requestBody,
        ParameterizedTypeReference<T> responseType2, Object... uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody);
        return restTemplate.exchange(url, method, requestEntity, responseType2).getBody();
    }
}
