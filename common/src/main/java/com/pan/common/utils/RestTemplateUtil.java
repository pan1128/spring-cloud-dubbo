package com.pan.common.utils;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class RestTemplateUtil {

    @Resource
    private RestTemplate restTemplate;


    /**
     * This method sends an HTTP request to the specified URL using the provided HTTP method.
     * It includes a request body, headers, and URI variables if applicable.
     * It then receives and returns the response body in the specified parameterized type.
     *
     * @param url           the URL to send the request to
     * @param httpMethod    the HTTP method to use for the request (GET, POST, PUT, DELETE, etc.)
     * @param requestBody  the request body to include in the request
     * @param headersMap    a map of headers to include in the request
     * @param typeRef       the parameterized type reference representing the expected response type
     * @param uriVariables  a map of URI variables to include in the request URL
     * @return              the response body in the specified parameterized type
     */
    public <T> T exchange(String url, HttpMethod httpMethod,Object requestBody, Map<String, String> headersMap,ParameterizedTypeReference<T> typeRef,Map<String,Object> uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        if (!CollectionUtils.isEmpty(headersMap)) {
            headersMap.forEach((k, v) -> headers.add(k, v));
        }
        HttpEntity requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, httpMethod, requestEntity, typeRef, uriVariables);
        return responseEntity.getBody();
    }
}
