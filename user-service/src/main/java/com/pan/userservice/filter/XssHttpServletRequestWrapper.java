package com.pan.userservice.filter;

import com.pan.common.utils.XssUtil;
import org.apache.catalina.util.ParameterMap;
import org.springframework.util.FileCopyUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private byte[] body;

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        try (
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        ) {
           /* ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            FileCopyUtils.copy(request.getInputStream(),byteArrayOutputStream);
            body= byteArrayOutputStream.toByteArray();*/
            StringBuilder sb = new StringBuilder();
            String temp = "";
            while ((temp = bufferedReader.readLine()) != null) {
                sb.append(temp);
            }
            String bodyString = sb.toString();

            body = XssUtil.xssEncode(bodyString).getBytes(StandardCharsets.UTF_8);
        } catch (IOException e) {
        }
    }

    @Override
    public String getParameter(String name) {
        String parameter = super.getParameter(name);
        return XssUtil.xssEncode(parameter);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameterMap = super.getParameterMap();
        ParameterMap<String, String[]> newMap = new ParameterMap<>();

        parameterMap.entrySet().stream().forEach(entry->{
            String[] parameterValues = getParameterValues(entry.getKey());
            for (int i = 0; i < parameterValues.length; i++) {
                parameterValues[i] = XssUtil.xssEncode(parameterValues[i]);
            }
            newMap.put(entry.getKey(), parameterValues);
        });
        newMap.setLocked(true);
        return newMap;
        // 下面代码会抛出异常No modifications are allowed to a locked ParameterMap
        /*Enumeration<String> enumerator = super.getParameterNames();
        Map<String, String[]> pMap = super.getParameterMap();
        while (enumerator.hasMoreElements()) {
            String paraName = enumerator.nextElement();
            String[] value = pMap.get(paraName);
            if (value.length == 0) {
                continue;
            }
            for (int i = 0; i < value.length; i++) {
                value[i] = XssUtil.xssEncode(value[i]);
            }
            pMap.put(paraName, value);
        }
        return pMap;*/
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return super.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameterValues = super.getParameterValues(name);
        for (int i = 0; i < parameterValues.length; i++) {
            parameterValues[i] = XssUtil.xssEncode(parameterValues[i]);
        }
        return parameterValues;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {
            }

            @Override
            public int read() throws IOException {
                return inputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
