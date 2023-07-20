package com.pan.common.config;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

@Configuration
public class Jack2jsonConfig {
    private static final String pattern = "yyyy-MM-dd HH:mm:ss";
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder
                    //序列化null对象 是否报错
                    .failOnEmptyBeans(false)
                    //反序列化时 json含有bean不存在的属性 是否报错
                    .failOnUnknownProperties(false)
                    //序列化 反序列化 Date Calendar日期格式化
                    .dateFormat(new SimpleDateFormat(pattern))
                    //时区 格林乔治时间+8 为北京时间
                    .timeZone("GMT+8")
                    //序列化LocalDate
                    .serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    //序列化LocalTime
                    .serializers(new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")))
                    //序列化LocalDateTime
                    .serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern)))
                    //反序列化LocalDate
                    .deserializers(new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    //反序列化LocalTime
                    .deserializers(new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")))
                    //反序列化LocalDateTime
                    .deserializers(new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(pattern)))
                    //序列化忽略null属性
                    .serializationInclusion(JsonInclude.Include.NON_NULL);
        };
    }
}
