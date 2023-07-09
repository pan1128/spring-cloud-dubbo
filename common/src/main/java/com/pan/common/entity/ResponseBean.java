package com.pan.common.entity;


public class ResponseBean<T> {

    private String msg;
    private T data;
    private Integer code;


    public ResponseBean(String msg, T data, Integer code) {
        this.msg = msg;
        this.data = data;
        this.code = code;
    }

    public static <T> ResponseBean success(T data){
        return new ResponseBean("请求成功", data, 200);
    }
    public static <T> ResponseBean success(T data, String msg){
        return new ResponseBean(msg, data, 200);
    }
    public static <T> ResponseBean fail(String msg){
        return new ResponseBean(msg, null, 500);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
