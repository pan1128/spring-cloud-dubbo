package com.pan.userservice.common;


public class RespenseBean<T> {

    private String msg;
    private T data;
    private Integer code;


    public RespenseBean(String msg, T data, Integer code) {
        this.msg = msg;
        this.data = data;
        this.code = code;
    }

    public static <T> RespenseBean success(T data){
        return new RespenseBean("请求成功", data, 200);
    }
    public static <T> RespenseBean success(T data,String msg){
        return new RespenseBean(msg, data, 200);
    }
    public static <T> RespenseBean fail(String msg){
        return new RespenseBean(msg, null, 500);
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
