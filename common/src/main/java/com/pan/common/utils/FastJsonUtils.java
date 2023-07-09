package com.pan.common.utils;

import com.alibaba.fastjson2.*;
import lombok.Data;

import java.util.Date;
import java.util.Map;

public class FastJsonUtils {

    @Data
    static class Student{
        private Date date;
        private String name;
    }
    /**
     * 对象 转 json字符串
     * @param obj
     * @return
     */
    public static String obj2Json(Object obj) {
        JSON.configWriterDateFormat("yyyy-MM-dd HH:mm:ss");
        String jsonString = JSONObject.toJSONString(obj);
        return jsonString;
    }


    /**
     * json字符串转对象
     * @param json
     * @param reference
     * @return
     * @param <T>
     */
    public static <T> T json2Obj(String json,TypeReference<T> reference) {
        //Configure the date format of the JSON object
        JSON.configReaderDateFormat("yyyy-MM-dd HH:mm:ss");
        //Parse the JSON object to the specified type
        T t = JSON.parseObject(json, reference);
        //Return the parsed object
        return t;
    }

    public static void main(String[] args) {
        Student student = new Student();
        student.setDate(new Date());
        student.setName("ssl");

        System.out.println(obj2Json(student));

        TypeReference<Map<String,Student>> reference = new TypeReference<Map<String,Student>>() {
        };
        Map<String,Student> obj = json2Obj("{\n" +
                "\"obj\":{\"date\":\"2023-07-04 21:11:47\",\"name\":\"ssl\",\"age\":22}\n" +
                "}", reference);
        System.out.println(obj);
    }



}
