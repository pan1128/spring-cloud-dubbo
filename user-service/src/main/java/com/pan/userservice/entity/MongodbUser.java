package com.pan.userservice.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Document("comment")
public class MongodbUser implements Serializable {

    @Id
    private String id;

    @Field("content")
    private String content;

    private LocalDateTime time;

    private Integer age;
}
